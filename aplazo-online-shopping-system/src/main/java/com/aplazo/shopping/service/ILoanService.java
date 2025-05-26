/**
 * 
 */
package com.aplazo.shopping.service;

import java.util.List;
import java.util.UUID;

import com.aplazo.shopping.components.schemas.request.LoanRequest;
import com.aplazo.shopping.model.dao.Loan;

/**
 * @author CesarSalazar
 */
public interface ILoanService {
	Loan findById(UUID id);
	Loan save(LoanRequest loan);
	List<Loan> findAllByIdCreditLine(UUID idLoan);
}
