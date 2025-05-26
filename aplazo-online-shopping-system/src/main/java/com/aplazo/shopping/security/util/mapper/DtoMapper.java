/**
 * 
 */
package com.aplazo.shopping.security.util.mapper;

import com.aplazo.shopping.components.schemas.request.CustomerRequest;
import com.aplazo.shopping.model.dao.Customer;
import com.aplazo.shopping.model.dao.User;
import com.aplazo.shopping.security.config.request.LoginRequest;

/**
 * 
 */
public class DtoMapper {
	public static User signUpToUser(LoginRequest request) {
		return User.builder()
				.email(request.getEmail())
				.password(request.getPassword())
				.build();
	}
	
	public static Customer customerRequestToCustomer(CustomerRequest request) {
		return Customer.builder()
				.firstName(request.getFirstName())
				.lastName(request.getLastName())
				.secondLastName(request.getSecondLastName())
				.dateOfBirth(request.getDateOfBirth())
				.build();
	}
}
