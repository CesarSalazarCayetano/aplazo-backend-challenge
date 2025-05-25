/**
 * 
 */
package com.aplazo.shopping.service;

import java.util.Date;

import com.aplazo.shopping.model.dao.Customer;

/**
 * @author CesarSalazar
 */
public interface ICustomerService {
	Customer save(Customer customer);
	Integer isValidDateOfBirt(Date dateOfBirth);
}
