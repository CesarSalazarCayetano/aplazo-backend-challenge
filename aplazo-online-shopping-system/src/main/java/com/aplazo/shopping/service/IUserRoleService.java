/**
 * 
 */
package com.aplazo.shopping.service;

import java.util.List;

import com.aplazo.shopping.model.dao.UserRole;

/**
 * @author CesarSalazar
 */
public interface IUserRoleService {

	List<UserRole> findAll();
	UserRole getByUserRole(String nameRole);
	UserRole save(UserRole userRole);
}
