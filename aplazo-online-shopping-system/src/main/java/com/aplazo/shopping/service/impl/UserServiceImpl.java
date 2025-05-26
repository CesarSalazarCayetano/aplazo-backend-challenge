/**
 * 
 */
package com.aplazo.shopping.service.impl;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aplazo.shopping.model.dao.User;
import com.aplazo.shopping.model.dao.UserRole;
import com.aplazo.shopping.repository.IUserRepository;
import com.aplazo.shopping.security.config.request.LoginRequest;
import com.aplazo.shopping.security.util.mapper.DtoMapper;
import com.aplazo.shopping.service.IUserRoleService;
import com.aplazo.shopping.service.IUserService;

/**
 * @author CesarSalazar
 */
@Service
public class UserServiceImpl implements IUserService {

	@Autowired
	PasswordEncoder crypt;
	@Autowired
	private IUserRoleService iUserRoleService;
	@Autowired
	private IUserRepository iUserRepository;
	
	@Transactional(rollbackFor = {IllegalArgumentException.class, SQLException.class})
	@Override
	public User save(LoginRequest loginRequest) {
		User userFromDTO = DtoMapper.signUpToUser(loginRequest);
	
		UserRole userRole = this.iUserRoleService.getByUserRole("USER"); 
		userFromDTO.setUserRole(userRole);
		userFromDTO.setPassword(crypt.encode(userFromDTO.getPassword()));
		
		User saveUser = this.iUserRepository.save(userFromDTO);

		return saveUser;
	}

	@Transactional(readOnly = true)
	@Override
	public User findByEmail(String email) {
		return this.iUserRepository.findByEmail(email);
	}
	
	@Override
	public boolean validateEmailExist(String email) {
		if(this.iUserRepository.findByEmail(email) != null) {
			return true;
		}
		return false;
	}
	
}
