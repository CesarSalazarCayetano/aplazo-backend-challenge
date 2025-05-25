/**
 * 
 */
package com.aplazo.shopping.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aplazo.shopping.model.dao.User;

/**
 * @author CesarSalazar
 */
@Repository
public interface IUserRepository extends JpaRepository<User, Long> {
	User findByEmail(String email);
}
