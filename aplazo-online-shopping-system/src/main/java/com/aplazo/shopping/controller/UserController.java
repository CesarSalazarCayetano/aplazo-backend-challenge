/**
 * 
 */
package com.aplazo.shopping.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aplazo.shopping.controller.utils.ControllerUtils;
import com.aplazo.shopping.enums.ErrorCode;
import com.aplazo.shopping.exception.ApiException;
import com.aplazo.shopping.model.dao.User;
import com.aplazo.shopping.response.http.ApiResponseEntityData;
import com.aplazo.shopping.security.config.request.LoginRequest;
import com.aplazo.shopping.security.config.response.JwtResponse;
import com.aplazo.shopping.security.service.JWTUserDetailsService;
import com.aplazo.shopping.security.util.JWTUtils;
import com.aplazo.shopping.service.IUserService;

import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

/**
 * @author CesarSalazar
 */
@Tag(name = "REST Service for User", description = "This service manage auth methods for autenticate.")
@RestController
@RequestMapping("/public/user")
public class UserController {
	
	@Autowired
	private IUserService iUserService;
	@Autowired
	private JWTUserDetailsService jwtUserDetailsService;
	@Autowired
	private AuthenticationManager authenticationManager;

	@Operation(summary = "login", description = "This method authenticated on the API, this must have called after signUp.")
	@ApiResponses(value = {
            @ApiResponse (responseCode = "201", description = "User was created"),
            @ApiResponse (responseCode = "400", description = "Bad request, can be invalid body data")
		}
	)
	@RateLimiter(name = "public-api")
	@PostMapping(path = "/login", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> login(@RequestBody @Valid LoginRequest loginRequest, BindingResult bindingResult) {
		ControllerUtils.validateFields(bindingResult, loginRequest.getEmail());
		if(!this.iUserService.validateEmailExist(loginRequest.getEmail())) {
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
		JwtResponse response = new JwtResponse(loginRequest.getEmail(), token);
		return new ApiResponseEntityData<>().responseEntitySuccessData(null, HttpStatus.OK, "Login success", response);
	}
	
	@ApiResponses(value = {
            @ApiResponse (responseCode = "201", description = "User was created"),
            @ApiResponse (responseCode = "400", description = "Bad request, can be invalid body data or duplicated data")
		}
	)
	@Operation(summary = "sing-up", description = "This method register a new user on the API.")
	@RateLimiter(name = "public-api")
	@PostMapping(path = "/sign-up", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> signUp(@RequestBody @Valid LoginRequest loginRequest, BindingResult bindingResult) {
		ControllerUtils.validateFields(bindingResult, loginRequest.getEmail());
		if(this.iUserService.validateEmailExist(loginRequest.getEmail())) {
			throw new ApiException(ErrorCode.USER_ALREADY_EXIST);
		}
		User user = this.iUserService.save(loginRequest);
		
		final UserDetails userDetails = this.jwtUserDetailsService.loadUserByUsername(loginRequest.getEmail());
		final String token = JWTUtils.generateToken(user.getEmail(), userDetails);
		
		JwtResponse response = new JwtResponse(loginRequest.getEmail(), token);
		
		return new ApiResponseEntityData<>().responseEntitySuccessData(null, HttpStatus.CREATED, "User save", response);
	}
	
}
