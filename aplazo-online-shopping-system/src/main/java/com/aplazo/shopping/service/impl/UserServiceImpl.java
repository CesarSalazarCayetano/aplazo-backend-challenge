/**
 * 
 */
package com.aplazo.shopping.service.impl;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aplazo.shopping.model.dao.Customer;
import com.aplazo.shopping.model.dao.User;
import com.aplazo.shopping.model.dao.UserRole;
import com.aplazo.shopping.repository.IUserRepository;
import com.aplazo.shopping.security.config.request.SignUpRequest;
import com.aplazo.shopping.security.util.mapper.DtoMapper;
import com.aplazo.shopping.service.ICreditLineService;
import com.aplazo.shopping.service.ICustomerService;
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
	@Autowired
	private ICustomerService iCustomerservice;
	@Autowired
	private ICreditLineService iCreditLineService;
	
	@Transactional(rollbackFor = {IllegalArgumentException.class, SQLException.class})
	@Override
	public User save(SignUpRequest signUpRequest) {
		Customer customerFromDTO = DtoMapper.signUpToCustomer(signUpRequest);
		User userFromDTO = DtoMapper.signUpToUser(signUpRequest);
		userFromDTO.setCustomer(customerFromDTO);
		
		UserRole userRole = this.iUserRoleService.getByUserRole("USER"); 
		userFromDTO.setUserRole(userRole);
		userFromDTO.setPassword(crypt.encode(userFromDTO.getPassword()));
		
		User saveUser = this.iUserRepository.save(userFromDTO);
		
		Customer customer = userFromDTO.getCustomer();
		customer.setUser(saveUser);
		
		Customer saveCustomer = this.iCustomerservice.save(customer);
		
		this.iCreditLineService.creditLineAssignment(saveCustomer, signUpRequest.getAge());
		
		return saveUser;
	}

	@Transactional(readOnly = true)
	@Override
	public User findByEmail(String email) {
		return this.iUserRepository.findByEmail(email);
	}
	
}
