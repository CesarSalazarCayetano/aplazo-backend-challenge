/**
 * 
 */
package com.aplazo.shopping.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.aplazo.shopping.model.dao.Payment;
import com.aplazo.shopping.enums.InstallmentStatus;


/**
 * @author CesarSalazar
 */
@Repository
public interface IPaymentRepository extends JpaRepository<Payment, UUID> {
	
	@Query("SELECT p FROM "
			+ "Payment p "
			+ "JOIN p.loan l "
			+ "WHERE l.idLoan = ?1")
	List<Payment> findByIdLoan(UUID idLoan);
	
	List<Payment> findByPaymentStatus(InstallmentStatus paymentStatus);
}
