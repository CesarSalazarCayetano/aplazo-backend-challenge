/**
 * 
 */
package com.aplazo.shopping.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aplazo.shopping.model.dao.PaymentScheme;

/**
 * @author CesarSalazar
 */
@Repository
public interface IPaymentSchemeRepository extends JpaRepository<PaymentScheme, Integer> {

	PaymentScheme findByPaymentScheme(String paymentScheme);
}
