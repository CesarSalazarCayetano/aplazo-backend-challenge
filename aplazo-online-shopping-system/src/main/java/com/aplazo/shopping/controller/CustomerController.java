/**
 * 
 */
package com.aplazo.shopping.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aplazo.shopping.components.schemas.request.CustomerRequest;
import com.aplazo.shopping.components.schemas.response.CustomerResponse;
import com.aplazo.shopping.controller.utils.ControllerUtils;
import com.aplazo.shopping.enums.ErrorCode;
import com.aplazo.shopping.exception.ApiException;
import com.aplazo.shopping.model.dao.Customer;
import com.aplazo.shopping.model.dao.User;
import com.aplazo.shopping.response.http.ApiResponseEntityData;
import com.aplazo.shopping.security.util.JWTUtils;
import com.aplazo.shopping.service.ICustomerService;
import com.aplazo.shopping.service.IUserService;

import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;

/**
 * 
 */
@Tag(name = "REST Service for Customer", description = "This service manage customer credit lines.")
@RestController
@RequestMapping("/protected/customers")
@RateLimiter(name = "protected-api")
@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
public class CustomerController {
	
	@Autowired
	private IUserService iUserService;
	@Autowired
	private ICustomerService iCustomerService;

	@Operation(summary = "findCustomer", description = "This method find the info by id customer.")
	@ApiResponses(value = {
            @ApiResponse (responseCode = "200", description = "The data was returned successfully"),
            @ApiResponse (responseCode = "404", description = "Not found if the customer doesnt exist")
		}
	)
	@GetMapping(path = "/{id}")
	public ResponseEntity<?> findCustomer(@RequestHeader(name = JWTUtils.HEADER) String token,
			@PathVariable UUID id) {
		Customer customer = this.iCustomerService.findById(id);
		
		CustomerResponse customerResponse = new CustomerResponse(customer.getIdCustomer(), 
				customer.getCreditLine().getCreditLineRule().getCreditLineAmount(), 
				customer.getCreditLine().getAvailableCreditLineAmount(), 
				customer.getCreatedAt());
		
		return new ApiResponseEntityData<>().responseEntitySuccessData(null, HttpStatus.OK, null, customerResponse);
	}
	
	@Operation(summary = "createCustomer", description = "This method create a new customer and after his credit line.")
	@ApiResponses(value = {
            @ApiResponse (responseCode = "201", description = "The customer was created successfully"),
            @ApiResponse (responseCode = "400", description = "Bad request if exist errors on body fields or the age are not valid")
		}
	)
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> createCustomer(HttpServletRequest request, 
			@RequestHeader(name = JWTUtils.HEADER) String token,
			@RequestBody CustomerRequest customerRequest, BindingResult bindingResult) {
		String userName = JWTUtils.getUserNameFromToken(JWTUtils.existHeaderJWT(token));
		User user = this.iUserService.findByEmail(userName);
		if(this.iCustomerService.findUserCustomer(user.getIdUser()) != null) {
			throw new ApiException(ErrorCode.CUSTOMER_ALREADY_EXIST);
		}
		
		ControllerUtils.validateFields(bindingResult, userName);
		
		Integer age = this.iCustomerService.isValidDateOfBirt(customerRequest.getDateOfBirth());
		if(age == null) {
			throw new ApiException(ErrorCode.BAD_AGE);
		}
		customerRequest.setAge(age);
		customerRequest.setEmail(userName);
		Customer customer = this.iCustomerService.save(customerRequest);
		
		CustomerResponse customerResponse = new CustomerResponse(customer.getIdCustomer(), 
				customer.getCreditLine().getCreditLineRule().getCreditLineAmount(), 
				customer.getCreditLine().getAvailableCreditLineAmount(), 
				customer.getCreatedAt());
		
		return new ApiResponseEntityData<>().responseEntitySuccessData(null, HttpStatus.CREATED, "Customer created", customerResponse);
	}
	
	
}
