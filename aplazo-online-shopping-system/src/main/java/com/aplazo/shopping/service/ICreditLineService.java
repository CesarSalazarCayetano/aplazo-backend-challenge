/**
 * 
 */
package com.aplazo.shopping.service;

import com.aplazo.shopping.model.dao.CreditLine;
import com.aplazo.shopping.model.dao.Customer;

/**
 * @author CesarSalazar
 */
public interface ICreditLineService {
	CreditLine creditLineAssignment(Customer creditLine, Integer age);
}
