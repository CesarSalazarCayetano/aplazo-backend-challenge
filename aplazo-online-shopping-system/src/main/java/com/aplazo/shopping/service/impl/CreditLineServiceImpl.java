/**
 * 
 */
package com.aplazo.shopping.service.impl;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aplazo.shopping.model.dao.CreditLine;
import com.aplazo.shopping.model.dao.CreditLineRule;
import com.aplazo.shopping.model.dao.Customer;
import com.aplazo.shopping.repository.ICreditLineRepository;
import com.aplazo.shopping.service.ICreditLineRuleService;
import com.aplazo.shopping.service.ICreditLineService;

/**
 * @author CesarSalazar
 */
@Service
public class CreditLineServiceImpl implements ICreditLineService {

	@Autowired
	private ICreditLineRepository iCreditLineRepository;
	@Autowired
	private ICreditLineRuleService iCreditLineRuleService;
	
	@Override
	public CreditLine creditLineAssignment(Customer customer, Integer age) {
		CreditLineRule creditLineRule = this.iCreditLineRuleService.getCreditLineRuleByAge(age);
		
		CreditLine creditLine = CreditLine.builder()
				.customer(customer)
				.availableCreditLineAmount(creditLineRule.getCreditLineAmount())
				.creditLineRule(creditLineRule).build();
		
		return this.iCreditLineRepository.save(creditLine);
	}

	@Transactional(rollbackFor = {IllegalArgumentException.class, SQLException.class})
	@Override
	public CreditLine update(CreditLine creditLine) {
		return this.iCreditLineRepository.save(creditLine);
	}
	
}
