/**
 * 
 */
package com.aplazo.shopping.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aplazo.shopping.components.schemas.request.LoanRequest;
import com.aplazo.shopping.enums.ErrorCode;
import com.aplazo.shopping.enums.LoanStatus;
import com.aplazo.shopping.exception.ApiException;
import com.aplazo.shopping.model.dao.CreditLine;
import com.aplazo.shopping.model.dao.Customer;
import com.aplazo.shopping.model.dao.Loan;
import com.aplazo.shopping.model.dao.PaymentScheme;
import com.aplazo.shopping.repository.ILoanRepository;
import com.aplazo.shopping.service.ICreditLineService;
import com.aplazo.shopping.service.ICustomerService;
import com.aplazo.shopping.service.ILoanService;
import com.aplazo.shopping.service.IPaymentSchemeService;

/**
 * @author CesarSalazar
 */
@Service
public class LoanServiceImpl implements ILoanService {

	private static final int ID_CLIENT_SCHEME = 25;
	private static final String SCHEME_1 = "Scheme 1";
	private static final String SCHEME_2 = "Scheme 2";
	private static final Character[] CHAR_START_WITH = {'C', 'L', 'H'};
	
	@Autowired
	private ILoanRepository iLoanRepository;
	@Autowired
	private ICreditLineService iCreditLineService;
	@Autowired
	private ICustomerService iCustomerService;
	@Autowired
	private IPaymentSchemeService iPaymentSchemeService;
	
	@Override
	public Loan findById(UUID id) {
		return this.iLoanRepository.findById(id).orElseThrow(() -> new ApiException(ErrorCode.LOAN_NOT_FOUND));
	}

	@Override
	public List<Loan> findAllByIdCreditLine(UUID idLoan) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Loan save(LoanRequest loanRequest) {
		Customer customer = this.iCustomerService.findById(loanRequest.getIdCustomer());
		PaymentScheme paymentScheme = this.assignmentScheme(customer.getFirstName(), customer.getCreditLine().getIdCreditLine());
		
		Double availableCreditLineAmount = calculateAvailableCreditLineAmount(
				customer.getCreditLine().getAvailableCreditLineAmount(), loanRequest.getAmount());
		Double totalAmount = this.calculateTotalAmount(paymentScheme.getInterestRate(), loanRequest.getAmount());
		
		CreditLine creditLine = customer.getCreditLine();
		creditLine.setAvailableCreditLineAmount(availableCreditLineAmount);
		
		Loan loan = Loan.builder()
		.purchaseCommission(this.calculatePurchaseComission(paymentScheme.getInterestRate(), loanRequest.getAmount()))
		.amountLoan(loanRequest.getAmount())
		.totalAmount(totalAmount)
		.amountBySchedule(this.calculateAmountBySchedule(paymentScheme.getNumberOfPayments(), totalAmount))
		.loanStatus(LoanStatus.ACTIVE)
		.dateSchedulePayment(this.generateSchedulePayment(paymentScheme.getNumberOfPayments()))
		.creditLine(creditLine)
		.paymentScheme(paymentScheme)
		.build();
		
		Loan loanSave = this.iLoanRepository.save(loan);
		CreditLine creditLineUpdate = this.iCreditLineService.update(creditLine);
		
		return loanSave;
	}

	private PaymentScheme assignmentScheme(String customerName, Long idCreditLine) {
		customerName = customerName.trim().toUpperCase();
		char initialChar = customerName.charAt(0);
		
		String scheme = "";
		boolean isScheme1 = false;
		
		for (char charStart : CHAR_START_WITH) {
			if(initialChar == charStart) {
				isScheme1 = true;
				continue;
			}
		}
		if(isScheme1) scheme = SCHEME_1;
		if(idCreditLine > ID_CLIENT_SCHEME || isScheme1 == false) scheme = SCHEME_2;
		PaymentScheme paymentScheme = this.iPaymentSchemeService.findByPaymentScheme(scheme);
		return paymentScheme;
	}

	private String generateSchedulePayment(int numberOfPayments) {
		LocalDateTime temp = null; 
		LocalDateTime now = LocalDateTime.now();
		String dateSchedule = "";
		for (int i = 0; i < numberOfPayments; i++) {
			if(temp == null) {
				temp = now.plusDays(14);
			} else {
				temp = temp.plusDays(14);
			}
			dateSchedule += temp + ", ";
		} 
		dateSchedule = StringUtils.removeEnd(dateSchedule, ", ");
		return dateSchedule;
	}
	
	private Double calculateAvailableCreditLineAmount(Double availableCreditLineAmount, Double amount) {
		if(availableCreditLineAmount < amount) {
			throw new ApiException(ErrorCode.CREDIT_LINE_EXCEEDED);
		}
		Double result = availableCreditLineAmount - amount;
		return result;
	}
	
	private Double calculatePurchaseComission(Integer interestRate, Double amount) {
		Double purchaseCommission = (amount / 100) * interestRate;
		return purchaseCommission;
	}
	
	private Double calculateTotalAmount(Integer interestRate, Double amount) {
		Double purchaseCommission = this.calculatePurchaseComission(interestRate, amount);
		Double result = purchaseCommission + amount;
		return result;
	}
	
	private Double calculateAmountBySchedule(int numberOfPayments, Double totalAmount) {
		Double amountBySchedule = totalAmount / numberOfPayments;
		return amountBySchedule;
	}

	@Override
	public Loan update(Loan loan) {
		return this.iLoanRepository.save(loan);
	}
	
}
