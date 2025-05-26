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
import jakarta.validation.Valid;

/**
 * @author CesarSalazar
 */
@RestController
@RequestMapping("/protected/loans")
@RateLimiter(name = "protected-api")
@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
public class LoanController {

	@Autowired
	private ILoanService iLoanService;
	
	@GetMapping(path = "/{idLoan}")
	public ResponseEntity<?> getMethodName(
			@RequestHeader(name = JWTUtils.HEADER) String token,
			@PathVariable UUID idLoan) {
		Loan loan = this.iLoanService.findById(idLoan);
		LoanResponse loanResponse = new LoanResponse(idLoan, 
				loan.getCreditLine().getCustomer().getIdCustomer(), 
				loan.getAmountLoan(), 
				loan.getCreatedAt());
		return new ApiResponseEntityData<>().responseEntitySuccessData(null, HttpStatus.OK, null, loanResponse);
	}
	
	@PostMapping()
	public ResponseEntity<?> postMethodName(
			@RequestHeader(name = JWTUtils.HEADER) String token,
			@RequestBody @Valid LoanRequest loanRequest, BindingResult bindingResult) {
		ControllerUtils.validateFields(bindingResult, null);
		
		Loan loan = this.iLoanService.save(loanRequest);
		
		LoanResponse loanResponse = new LoanResponse(loan.getIdLoan(), 
				loan.getCreditLine().getCustomer().getIdCustomer(), 
				loan.getAmountLoan(), 
				loan.getCreatedAt());
		return new ApiResponseEntityData<>().responseEntitySuccessData(null, HttpStatus.OK, null, loanResponse);
	}
	
	
}
