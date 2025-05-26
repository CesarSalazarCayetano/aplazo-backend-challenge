/**
 * 
 */
package com.aplazo.shopping.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aplazo.shopping.enums.ErrorCode;
import com.aplazo.shopping.enums.InstallmentStatus;
import com.aplazo.shopping.enums.LoanStatus;
import com.aplazo.shopping.exception.ApiException;
import com.aplazo.shopping.model.dao.Loan;
import com.aplazo.shopping.model.dao.Payment;
import com.aplazo.shopping.repository.IPaymentRepository;
import com.aplazo.shopping.service.ILoanService;
import com.aplazo.shopping.service.IPaymentService;

/**
 * @author CesarSalazar
 */
@Service
public class PaymentServiceImpl implements IPaymentService {

	@Autowired
	private IPaymentRepository iPaymentRepository;
	@Autowired
	private ILoanService iLoanService;
	
	@Override
	public Payment findById(UUID id) {
		return this.iPaymentRepository.findById(id).orElseThrow(() -> new ApiException(ErrorCode.PAYMENT_NOT_FOUND));
	}

	@Override
	public Payment save(Payment payment, Loan loan) {
		String currentPayment = "";
		String[] scheduledPayments = loan.getDateSchedulePayment().split(",");
		List<Payment> listCurrentPayments = this.findAllByIdLoan(loan.getIdLoan());
		
		if(loan.getPaymentScheme().getNumberOfPayments() == listCurrentPayments.size()) {
			if(!loan.getLoanStatus().equals(LoanStatus.COMPLETED)) {
				loan.setLoanStatus(LoanStatus.COMPLETED);
				this.iLoanService.update(loan);
			}
			return null;
		}
		
		if(listCurrentPayments.size() != 0) {
			currentPayment = scheduledPayments[listCurrentPayments.size()];
		} else {
			currentPayment = scheduledPayments[0];
		}
		if(loan.getLoanStatus().equals(LoanStatus.ACTIVE)) {
			payment.setPaymentStatus(InstallmentStatus.NEXT);
		}
		if(loan.getLoanStatus().equals(LoanStatus.LATE)) {
			payment.setPaymentStatus(InstallmentStatus.PENDING);
		}
		payment.setLoan(loan);
		payment.setScheduledPaymentDate(currentPayment.trim().substring(0, 10));
		
		Payment paymentSave = this.iPaymentRepository.save(payment);
		
		return paymentSave;
	}

	@Override
	public List<Payment> findAllByIdLoan(UUID idLoan) {
		return this.iPaymentRepository.findByIdLoan(idLoan);
	}

	@Override
	public List<Payment> findByPaymentStatus(InstallmentStatus paymentStatus) {
		return this.iPaymentRepository.findByPaymentStatus(paymentStatus);
	}

}
