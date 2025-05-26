/**
 * 
 */
package com.aplazo.shopping.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aplazo.shopping.model.dao.Payment;

/**
 * @author CesarSalazar
 */
@Repository
public interface IPaymentRepository extends JpaRepository<Payment, UUID> {

}
