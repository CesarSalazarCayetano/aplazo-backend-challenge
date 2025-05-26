/**
 * 
 */
package com.aplazo.shopping.service;

import java.util.Date;
import java.util.UUID;

import com.aplazo.shopping.components.schemas.request.CustomerRequest;
import com.aplazo.shopping.model.dao.Customer;

/**
 * @author CesarSalazar
 */
public interface ICustomerService {
	Customer save(CustomerRequest customer);
	Integer isValidDateOfBirt(Date dateOfBirth);
	Customer findById(UUID id);
	Customer findUserCustomer(Long idUser);
}
