/**
 * 
 */
package com.aplazo.shopping.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aplazo.shopping.model.dao.CreditLineRule;

/**
 * 
 */
@Repository
public interface ICreditLineRuleRepository extends JpaRepository<CreditLineRule, Integer> {

}
