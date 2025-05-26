/**
 * 
 */
package com.aplazo.shopping.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aplazo.shopping.components.schemas.request.InstallmentRequest;
import com.aplazo.shopping.components.schemas.response.InstallmentResponse;
import com.aplazo.shopping.controller.utils.ControllerUtils;
import com.aplazo.shopping.model.dao.Loan;
import com.aplazo.shopping.model.dao.Payment;
import com.aplazo.shopping.response.http.ApiResponseEntityData;
import com.aplazo.shopping.security.util.JWTUtils;
import com.aplazo.shopping.service.ILoanService;
import com.aplazo.shopping.service.IPaymentService;

import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

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


/**
 * @author CesarSalazar
 */
@Tag(name = "REST Service for Installments", description = "This service manage installments for each loan.")
@RestController
@RateLimiter(name = "protected-api")
@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
@RequestMapping("/protected/installments")
public class InstallmentController {

	@Autowired
	private IPaymentService iPaymentService;
	@Autowired
	private ILoanService iLoanService;
	
	@Operation(summary = "findInstallment", description = "This method find the info installment by id.")
	@ApiResponses(value = {
            @ApiResponse (responseCode = "200", description = "The Installment data was returned successfully"),
            @ApiResponse (responseCode = "404", description = "Not found if payment doesnt exist")
		}
	)
	@GetMapping("/{idInstallment}")
	public ResponseEntity<?> findInstallment(
			@RequestHeader(name = JWTUtils.HEADER) String token,
			@PathVariable UUID idInstallment) {
		Payment payment = this.iPaymentService.findById(idInstallment);
		
		InstallmentResponse installmentResponse = new InstallmentResponse(payment.getIdPayment(), 
				payment.getAmount(), 
				payment.getScheduledPaymentDate(), 
				payment.getCreatedAt());
		
		return new ApiResponseEntityData<>().responseEntitySuccessData(null, HttpStatus.OK, null, installmentResponse);
	}
	
	@Operation(summary = "createInstallment", description = "This method create a new installment associated to a loan and this is associated to a line credit.")
	@ApiResponses(value = {
			@ApiResponse (responseCode = "200", description = "If they are not more installments to do"),
            @ApiResponse (responseCode = "201", description = "The Installment was created successfully"),
            @ApiResponse (responseCode = "400", description = "Bad request if exist errors on body fields"),
            @ApiResponse (responseCode = "404", description = "Not found if the Loan doesnt exist"),
		}
	)
	@PostMapping()
	public ResponseEntity<?> createInstallment(
			@RequestHeader(name = JWTUtils.HEADER) String token,
			@RequestBody @Valid InstallmentRequest installmentRequest,
			BindingResult bindingResult) {
		ControllerUtils.validateFields(bindingResult, null);
		Loan loan = this.iLoanService.findById(installmentRequest.getIdLoan());
		Payment payment = Payment.builder().amount(installmentRequest.getAmount()).build();
		
		payment = this.iPaymentService.save(payment, loan);
		
		if(payment == null) {
			return new ApiResponseEntityData<>().responseEntitySuccessData(null, HttpStatus.OK, "There are no more installments to do", null);
		}
		
		InstallmentResponse installmentResponse = new InstallmentResponse(payment.getIdPayment(), 
				payment.getAmount(), 
				payment.getScheduledPaymentDate(), 
				payment.getCreatedAt());
		
		return new ApiResponseEntityData<>().responseEntitySuccessData(null, HttpStatus.CREATED, "Success payment.", installmentResponse);
	}
	
}
