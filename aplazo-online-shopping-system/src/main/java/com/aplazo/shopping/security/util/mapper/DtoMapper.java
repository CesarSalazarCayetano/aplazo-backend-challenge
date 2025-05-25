/**
 * 
 */
package com.aplazo.shopping.security.util.mapper;

import com.aplazo.shopping.model.dao.Customer;
import com.aplazo.shopping.model.dao.User;
import com.aplazo.shopping.security.config.request.SignUpRequest;

/**
 * 
 */
public class DtoMapper {
	public static User signUpToUser(SignUpRequest request) {
		return User.builder()
				.email(request.getEmail())
				.password(request.getPassword())
				.role("USER")
				.build();
	}
	
	public static Customer signUpToCustomer(SignUpRequest request) {
		return Customer.builder()
				.firstName(request.getFirstName())
				.lastName(request.getLastName())
				.secondLastName(request.getSecondLastName())
				.dateOfBirth(request.getDateOfBirth())
				.build();
	}
}
