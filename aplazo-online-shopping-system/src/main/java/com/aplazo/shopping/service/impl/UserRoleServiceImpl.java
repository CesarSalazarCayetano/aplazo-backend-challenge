/**
 * 
 */
package com.aplazo.shopping.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aplazo.shopping.enums.ErrorCode;
import com.aplazo.shopping.exception.ApiException;
import com.aplazo.shopping.model.dao.UserRole;
import com.aplazo.shopping.repository.IUserRoleRepository;
import com.aplazo.shopping.service.IUserRoleService;

/**
 * @author CesarSalazar
 */
@Service
public class UserRoleServiceImpl implements IUserRoleService {

	@Autowired
	private IUserRoleRepository iUserRoleRepository;
	
	@Override
	public List<UserRole> findAll() {
		return this.iUserRoleRepository.findAll();
	}

	@Override
	public UserRole getByUserRole(String nameRole) {
		UserRole userRole = this.iUserRoleRepository.findByUserRole(nameRole);
		if(userRole == null) {
			throw new ApiException(ErrorCode.USER_NOT_FOUND);
		}
		return userRole;
	}

	@Override
	public UserRole save(UserRole userRole) {
		return this.iUserRoleRepository.save(userRole);
	}

}
