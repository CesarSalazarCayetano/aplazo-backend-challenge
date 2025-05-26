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
@RestController
@RateLimiter(name = "protected-api")
@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
@RequestMapping("/protected/installments")
public class InstallmentController {

	@Autowired
	private IPaymentService iPaymentService;
	@Autowired
	private ILoanService iLoanService;
	
	@GetMapping("/{idInstallment}")
	public ResponseEntity<?> getMethodName(
			@RequestHeader(name = JWTUtils.HEADER) String token,
			@PathVariable UUID idInstallment) {
		Payment payment = this.iPaymentService.findById(idInstallment);
		
		InstallmentResponse installmentResponse = new InstallmentResponse(payment.getIdPayment(), 
				payment.getAmount(), 
				payment.getScheduledPaymentDate(), 
				payment.getCreatedAt());
		
		return new ApiResponseEntityData<>().responseEntitySuccessData(null, HttpStatus.OK, null, installmentResponse);
	}
	
	@PostMapping()
	public ResponseEntity<?> postMethodName(
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
		
		return new ApiResponseEntityData<>().responseEntitySuccessData(null, HttpStatus.OK, "Success payment.", installmentResponse);
	}
	
}
