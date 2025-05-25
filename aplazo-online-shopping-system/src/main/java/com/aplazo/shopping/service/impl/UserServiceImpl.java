/**
 * 
 */
package com.aplazo.shopping.service.impl;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aplazo.shopping.model.dao.Customer;
import com.aplazo.shopping.model.dao.User;
import com.aplazo.shopping.repository.ICustomerRepository;
import com.aplazo.shopping.repository.IUserRepository;
import com.aplazo.shopping.service.IUserService;

/**
 * @author CesarSalazar
 */
@Service
public class UserServiceImpl implements IUserService {

	@Autowired
	PasswordEncoder crypt;
	@Autowired
	private IUserRepository iUserRepository;
	@Autowired
	private ICustomerRepository iCustomerRepository;
	
	@Transactional(rollbackFor = {IllegalArgumentException.class, SQLException.class})
	@Override
	public User save(User user) {
		user.setPassword(crypt.encode(user.getPassword()));
		User saveUser = this.iUserRepository.save(user);
		
		Customer customer = user.getCustomer();
		customer.setUser(saveUser);
		
		this.iCustomerRepository.save(customer);
		return saveUser;
	}

	@Transactional(readOnly = true)
	@Override
	public User findByEmail(String email) {
		return this.iUserRepository.findByEmail(email);
	}

	
}
