/**
 * 
 */
package com.aplazo.shopping.service;

import java.util.List;

import com.aplazo.shopping.model.dao.PaymentScheme;

/**
 * @author CesarSalazar 
 */
public interface IPaymentSchemeService {

	PaymentScheme findById(Integer id);
	PaymentScheme findByPaymentScheme(String paymentScheme);
	List<PaymentScheme> findAll();
}
