/**
 * 
 */
package com.aplazo.shopping.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aplazo.shopping.enums.ErrorCode;
import com.aplazo.shopping.exception.ApiException;
import com.aplazo.shopping.model.dao.CreditLineRule;
import com.aplazo.shopping.repository.ICreditLineRuleRepository;
import com.aplazo.shopping.service.ICreditLineRuleService;
import com.aplazo.shopping.util.RangeUtils;

/**
 * @author CesarSalazar
 */
@Service
public class CreditLineRuleServiceImpl implements ICreditLineRuleService {

	@Autowired
	private ICreditLineRuleRepository iCreditLineRuleRepository;
	
	@Transactional(readOnly = true)
	@Override
	public CreditLineRule findByCreditLineAmount(Double creditLineAmount) {
		return this.iCreditLineRuleRepository.findByCreditLineAmount(creditLineAmount);
	}

	@Transactional(readOnly = true)
	@Override
	public List<CreditLineRule> findByRuleName(String ruleName) {
		return this.iCreditLineRuleRepository.findByRuleName(ruleName);
	}
	
	@Override
	public CreditLineRule getCreditLineRuleByAge(Integer age) {
		List<CreditLineRule> listRules = this.findByRuleName("CPCLAR");
		return listRules.stream().filter(rule ->  {
			String[] ageRange = rule.getAgeRange().split(",");
			if(RangeUtils.getRangeFromString(age, ageRange[0], ageRange[1]) != null ) {
				return true;
			}
			return false;
		}).findFirst().orElseThrow(() -> new ApiException(ErrorCode.INTERNAL_SERVER_ERROR));
	}

}
