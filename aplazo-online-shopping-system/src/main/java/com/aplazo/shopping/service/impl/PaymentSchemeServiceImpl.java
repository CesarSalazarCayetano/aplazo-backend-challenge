/**
 * 
 */
package com.aplazo.shopping.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aplazo.shopping.enums.ErrorCode;
import com.aplazo.shopping.exception.ApiException;
import com.aplazo.shopping.model.dao.PaymentScheme;
import com.aplazo.shopping.repository.IPaymentSchemeRepository;
import com.aplazo.shopping.service.IPaymentSchemeService;

/**
 * @author CesarSalazar
 */
@Service
public class PaymentSchemeServiceImpl implements IPaymentSchemeService {

	@Autowired
	private IPaymentSchemeRepository iPaymentSchemeRepository;
	
	@Override
	public PaymentScheme findById(Integer id) {
		return this.iPaymentSchemeRepository.findById(id).orElseThrow(() -> new ApiException(ErrorCode.PAYMENT_SCHEME_NOT_FOUND));
	}

	@Override
	public List<PaymentScheme> findAll() {
		return this.iPaymentSchemeRepository.findAll();
	}

	@Override
	public PaymentScheme findByPaymentScheme(String paymentScheme) {
		PaymentScheme paymentSchemeResult = this.iPaymentSchemeRepository.findByPaymentScheme(paymentScheme);
		if(paymentSchemeResult == null) {
			throw new ApiException(ErrorCode.PAYMENT_SCHEME_NOT_FOUND);
		}
		return paymentSchemeResult;
	}

}
