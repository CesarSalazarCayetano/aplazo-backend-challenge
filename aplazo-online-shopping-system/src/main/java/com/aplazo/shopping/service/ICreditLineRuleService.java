/**
 * 
 */
package com.aplazo.shopping.service;

import java.util.List;

import com.aplazo.shopping.model.dao.CreditLineRule;

/**
 * @author CesarSalazar
 */
public interface ICreditLineRuleService {
	CreditLineRule getCreditLineRuleByAge(Integer age);
	List<CreditLineRule> findByRuleName(String ruleName);
	CreditLineRule findByCreditLineAmount(Double creditLineAmount);
}
