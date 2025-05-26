/**
 * 
 */
package com.aplazo.shopping.service;

import java.util.List;
import java.util.UUID;

import com.aplazo.shopping.enums.InstallmentStatus;
import com.aplazo.shopping.model.dao.Loan;
import com.aplazo.shopping.model.dao.Payment;

/**
 * @author CesarSalazar
 */
public interface IPaymentService {
	Payment findById(UUID id);
	Payment save(Payment payment, Loan loan);
	List<Payment> findAllByIdLoan(UUID idLoan);
	List<Payment> findByPaymentStatus(InstallmentStatus paymentStatus);
}
