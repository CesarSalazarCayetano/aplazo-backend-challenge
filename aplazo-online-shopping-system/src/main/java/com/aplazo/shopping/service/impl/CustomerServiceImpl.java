/**
 * 
 */
package com.aplazo.shopping.service.impl;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aplazo.shopping.enums.ErrorCode;
import com.aplazo.shopping.exception.ApiException;
import com.aplazo.shopping.model.dao.CreditLineRule;
import com.aplazo.shopping.model.dao.Customer;
import com.aplazo.shopping.repository.ICustomerRepository;
import com.aplazo.shopping.service.ICreditLineRuleService;
import com.aplazo.shopping.service.ICustomerService;
import com.aplazo.shopping.util.RangeUtils;

/**
 * @author CesarSalazar
 */
@Service
public class CustomerServiceImpl implements ICustomerService {

	@Autowired
	private ICustomerRepository iCustomerRepository;
	@Autowired
	private ICreditLineRuleService iCreditLineRuleService;
	
	@Transactional(rollbackFor = {IllegalArgumentException.class, SQLException.class})
	@Override
	public Customer save(Customer customer) {
		return this.iCustomerRepository.save(customer);
	}

	@Override
	public Integer isValidDateOfBirt(Date dateOfBirth) {
		LocalDate currentDate = LocalDate.now();
		LocalDate localDateBirth = dateOfBirth.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		int age = Period.between(localDateBirth, currentDate).getYears();
		if(age <= 0 ) throw new ApiException(ErrorCode.INVALID_CUSTOMER_REQUEST);
		
		CreditLineRule creditLineRule = this.iCreditLineRuleService.findByCreditLineAmount(0D);
		if(creditLineRule == null) throw new ApiException(ErrorCode.INVALID_CUSTOMER_REQUEST); 
		
		String[] ageRanges = creditLineRule.getAgeRange().split(",");
		
		return RangeUtils.getRangeFromString(age, ageRanges[0], ageRanges[1]);
	}


}
