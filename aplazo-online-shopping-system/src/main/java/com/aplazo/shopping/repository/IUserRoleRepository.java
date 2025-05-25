/**
 * 
 */
package com.aplazo.shopping.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aplazo.shopping.model.dao.UserRole;

/**
 * @author CesarSalazar
 */
@Repository
public interface IUserRoleRepository extends JpaRepository<UserRole, Integer> {
	UserRole findByUserRole(String userRole);
}
