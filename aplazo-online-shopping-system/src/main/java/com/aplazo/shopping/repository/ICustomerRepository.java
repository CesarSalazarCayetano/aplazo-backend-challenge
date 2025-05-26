/**
 * 
 */
package com.aplazo.shopping.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.aplazo.shopping.model.dao.Customer;

/**
 * @author CesarSalazar
 */
@Repository
public interface ICustomerRepository extends JpaRepository<Customer, UUID> {

	@Query("SELECT c FROM "
			+ "Customer c "
			+ "JOIN c.user u "
			+ "WHERE u.idUser = ?1")
	Customer findByIdUser(Long idUser);
}
