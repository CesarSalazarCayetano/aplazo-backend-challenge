/**
 * 
 */
package com.aplazo.shopping.service;

import java.util.Date;

import com.aplazo.shopping.components.schemas.request.CustomerRequest;
import com.aplazo.shopping.model.dao.Customer;

/**
 * @author CesarSalazar
 */
public interface ICustomerService {
	Customer save(CustomerRequest customer);
	Integer isValidDateOfBirt(Date dateOfBirth);
	Customer findUserCustomer(Long idUser);
}
