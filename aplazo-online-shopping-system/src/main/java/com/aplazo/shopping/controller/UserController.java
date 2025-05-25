/**
 * 
 */
package com.aplazo.shopping.controller;

import java.util.List;

import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aplazo.shopping.enums.ErrorCode;
import com.aplazo.shopping.exception.ApiException;
import com.aplazo.shopping.exception.utils.ExceptionUtils;
import com.aplazo.shopping.model.dao.Customer;
import com.aplazo.shopping.model.dao.User;
import com.aplazo.shopping.response.http.ApiResponseEntityData;
import com.aplazo.shopping.response.model.ApiResponse;
import com.aplazo.shopping.security.config.request.LoginRequest;
import com.aplazo.shopping.security.config.request.SignUpRequest;
import com.aplazo.shopping.security.service.JWTUserDetailsService;
import com.aplazo.shopping.security.util.JWTUtils;
import com.aplazo.shopping.security.util.mapper.DtoMapper;
import com.aplazo.shopping.service.IUserService;

import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import jakarta.validation.Valid;

/**
 * @author CesarSalazar
 */
@RestController
@RequestMapping("/public/user")
public class UserController {
	
	@Autowired
	private IUserService iUserService;
	@Autowired
	private JWTUserDetailsService jwtUserDetailsService;
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@RateLimiter(name = "public-api")
	@GetMapping("/test")
	public ResponseEntity<?> getMethodName() {
		if(true) {
			throw new ApiException(ErrorCode.INTERNAL_SERVER_ERROR);
		}
		return ResponseEntity.ok("hello world");
	}
	
	@GetMapping("/test-header")
	public ResponseEntity<?> getTestHeader() {
		
		return ResponseEntity.ok("hello world");
	}

	@RateLimiter(name = "public-api")
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody @Valid LoginRequest loginRequest, BindingResult bindingResult) {
		this.validateFields(bindingResult, loginRequest.getEmail());
		if(!this.validateEmailExist(loginRequest.getEmail())) {
			throw new ApiException(ErrorCode.BAD_USER_CREDENTIALS);
		}
		String token = null;
		final UserDetails userDetails = this.jwtUserDetailsService.loadUserByUsername(loginRequest.getEmail());
		try {
			this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
			token = JWTUtils.generateToken(loginRequest.getEmail(), userDetails);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApiException(ErrorCode.BAD_USER_CREDENTIALS);
		}
		return new ApiResponseEntityData<>().responseEntitySuccessData(null, HttpStatus.OK, "Login success", token);
	}
	
	@PostMapping("/sign-up")
	public ResponseEntity<?> signUp(@RequestBody @Valid SignUpRequest signUpRequest, BindingResult bindingResult) {
		this.validateFields(bindingResult, signUpRequest.getEmail());
		if(this.validateEmailExist(signUpRequest.getEmail())) {
			throw new ApiException(ErrorCode.USER_ALREADY_EXIST);
		}
		Customer customer = DtoMapper.signUpToCustomer(signUpRequest);
		User user = DtoMapper.signUpToUser(signUpRequest);
		user.setCustomer(customer);
		
		User userSave = this.iUserService.save(user);
		
		final UserDetails userDetails = this.jwtUserDetailsService.loadUserByUsername(signUpRequest.getEmail());
		final String token = JWTUtils.generateToken(user.getEmail(), userDetails);
		
		return new ApiResponseEntityData<>().responseEntitySuccessData(null, HttpStatus.CREATED, "User registred", token);
	}
	
	private void validateFields(BindingResult bindingResult, String email) {
		if(bindingResult.hasErrors()) {
			List<String> errors = ExceptionUtils.getErrorsFromBindingResult(bindingResult);
			throw new ApiException(ErrorCode.VALUES_NOT_VALID, errors);
		}
		if(!EmailValidator.getInstance().isValid(email)) {
			throw new ApiException(ErrorCode.EMAIL_NOT_VALID);
		}
	}
	private boolean validateEmailExist(String email) {
		if(this.iUserService.findByEmail(email) != null) {
			return true;
		}
		return false;
	}
}
