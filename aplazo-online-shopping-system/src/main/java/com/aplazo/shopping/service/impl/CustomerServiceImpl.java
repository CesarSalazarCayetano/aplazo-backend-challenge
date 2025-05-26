/**
 * 
 */
package com.aplazo.shopping.service.impl;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aplazo.shopping.components.schemas.request.CustomerRequest;
import com.aplazo.shopping.enums.ErrorCode;
import com.aplazo.shopping.exception.ApiException;
import com.aplazo.shopping.model.dao.CreditLine;
import com.aplazo.shopping.model.dao.CreditLineRule;
import com.aplazo.shopping.model.dao.Customer;
import com.aplazo.shopping.model.dao.User;
import com.aplazo.shopping.repository.ICustomerRepository;
import com.aplazo.shopping.security.util.mapper.DtoMapper;
import com.aplazo.shopping.service.ICreditLineRuleService;
import com.aplazo.shopping.service.ICreditLineService;
import com.aplazo.shopping.service.ICustomerService;
import com.aplazo.shopping.service.IUserService;
import com.aplazo.shopping.util.RangeUtils;

/**
 * @author CesarSalazar
 */
@Service
public class CustomerServiceImpl implements ICustomerService {

	@Autowired
	private IUserService iUserService;
	@Autowired
	private ICreditLineService iCreditLineService;
	@Autowired
	private ICustomerRepository iCustomerRepository;
	@Autowired
	private ICreditLineRuleService iCreditLineRuleService;
	
	@Transactional(rollbackFor = {IllegalArgumentException.class, SQLException.class})
	@Override
	public Customer save(CustomerRequest customerRequest) {
		Customer customer = DtoMapper.customerRequestToCustomer(customerRequest);
		User user = this.iUserService.findByEmail(customerRequest.getEmail());
		
		customer.setUser(user);
		
		Customer saveCustomer = this.iCustomerRepository.save(customer);
		
		CreditLine creditLine = this.iCreditLineService.creditLineAssignment(saveCustomer, customerRequest.getAge());
		saveCustomer.setCreditLine(creditLine);
		return saveCustomer;
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

	@Transactional(readOnly = true)
	@Override
	public Customer findUserCustomer(Long idUser) {
		return this.iCustomerRepository.findByIdUser(idUser);
	}

	@Transactional(readOnly = true)
	@Override
	public Customer findById(UUID id) {
		return this.iCustomerRepository.findById(id).orElseThrow(() -> new ApiException(ErrorCode.CUSTOMER_NOT_FOUND));
	}


}
