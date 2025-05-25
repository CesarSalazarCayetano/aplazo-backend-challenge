/**
 * 
 */
package com.aplazo.shopping.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aplazo.shopping.model.dao.CreditLineRule;
import java.util.List;


/**
 *  @author CesarSalazar 
 */
@Repository
public interface ICreditLineRuleRepository extends JpaRepository<CreditLineRule, Integer> {
	List<CreditLineRule> findByRuleName(String ruleName);
	CreditLineRule findByCreditLineAmount(Double creditLineAmount);
}
