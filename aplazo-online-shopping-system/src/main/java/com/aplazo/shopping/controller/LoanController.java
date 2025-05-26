/**
 * 
 */
package com.aplazo.shopping.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

import com.aplazo.shopping.components.schemas.request.LoanRequest;
import com.aplazo.shopping.components.schemas.response.LoanResponse;
import com.aplazo.shopping.controller.utils.ControllerUtils;
import com.aplazo.shopping.model.dao.Loan;
import com.aplazo.shopping.response.http.ApiResponseEntityData;
import com.aplazo.shopping.security.util.JWTUtils;
import com.aplazo.shopping.service.ILoanService;

import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

/**
 * @author CesarSalazar
 */
@Tag(name = "REST Service for Loan", description = "This service manage loans request.")
@RestController
@RequestMapping("/protected/loans")
@RateLimiter(name = "protected-api")
@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
public class LoanController {

	@Autowired
	private ILoanService iLoanService;
	
	@Operation(summary = "getLoan", description = "This method find the loan info by id.")
	@ApiResponses(value = {
            @ApiResponse (responseCode = "200", description = "The Loan was returned successfully"),
            @ApiResponse (responseCode = "404", description = "Not found if loan doesnt exist")
		}
	)
	@GetMapping(path = "/{idLoan}")
	public ResponseEntity<?> getLoan(
			@RequestHeader(name = JWTUtils.HEADER) String token,
			@PathVariable UUID idLoan) {
		Loan loan = this.iLoanService.findById(idLoan);
		LoanResponse loanResponse = new LoanResponse(idLoan, 
				loan.getCreditLine().getCustomer().getIdCustomer(), 
				loan.getAmountLoan(), 
				loan.getDateSchedulePayment(),
				loan.getCreatedAt());
		return new ApiResponseEntityData<>().responseEntitySuccessData(null, HttpStatus.OK, null, loanResponse);
	}
	
	@Operation(summary = "createLoan", description = "This method create a new loan associated to a line credit.")
	@ApiResponses(value = {
            @ApiResponse (responseCode = "201", description = "The Loan was created successfully"),
            @ApiResponse (responseCode = "400", description = "Bad request if exist errors on body fields, or some id or value relationated doesnt exist")
		}
	)
	@PostMapping()
	public ResponseEntity<?> createLoan(
			@RequestHeader(name = JWTUtils.HEADER) String token,
			@RequestBody @Valid LoanRequest loanRequest, BindingResult bindingResult) {
		ControllerUtils.validateFields(bindingResult, null);
		
		Loan loan = this.iLoanService.save(loanRequest);
		
		LoanResponse loanResponse = new LoanResponse(loan.getIdLoan(), 
				loan.getCreditLine().getCustomer().getIdCustomer(), 
				loan.getAmountLoan(),
				loan.getDateSchedulePayment(),
				loan.getCreatedAt());
		return new ApiResponseEntityData<>().responseEntitySuccessData(null, HttpStatus.CREATED, null, loanResponse);
	}
	
	
}
