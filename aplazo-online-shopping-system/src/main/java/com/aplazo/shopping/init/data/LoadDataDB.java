/**
 * 
 */
package com.aplazo.shopping.init.data;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.aplazo.shopping.model.dao.CreditLineRule;
import com.aplazo.shopping.model.dao.PaymentScheme;
import com.aplazo.shopping.repository.ICreditLineRuleRepository;
import com.aplazo.shopping.repository.IPaymentSchemeRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * Class to load the necesary data if the DB is empty.
 * @author CesarSalazar
 */
@Slf4j
@Component
public class LoadDataDB implements CommandLineRunner {

	@Autowired
	private ICreditLineRuleRepository iCreditLineRuleRepository;
	
	@Autowired
	private IPaymentSchemeRepository iPaymentSchemeRepository;
	
	@Override
	public void run(String... args) throws Exception {
		log.info("System started, validating if DB is empty...");
		this.validateCreditLineRulesExist();
		this.validatePaymentSchemeExist();
		log.info("System loaded.");
	}

	private void validateCreditLineRulesExist() {
		if(this.iCreditLineRuleRepository.findAll().size() == 0) {
			this.iCreditLineRuleRepository
			.saveAll(
				List.of(
					CreditLineRule.builder().ageRange("18,25").creditLineAmount(3000D).ruleName("CPCLAR").ruleDescription("$3,000 for clients aged 18 to 25 years.").build(),
					CreditLineRule.builder().ageRange("26,30").creditLineAmount(5000D).ruleName("CPCLAR").ruleDescription("$5,000 for clients aged 26 to 30 years.").build(),
					CreditLineRule.builder().ageRange("31,65").creditLineAmount(8000D).ruleName("CPCLAR").ruleDescription("$8,000 for clients aged 31 to 65 years.").build(),
					CreditLineRule.builder().ageRange("18,65").creditLineAmount(0D).ruleName("CPCLAR").ruleDescription("Clients under 18 or over 65 are not accepted.").build()
					)
				);
		}
	}

	private void validatePaymentSchemeExist() {
		if(this.iPaymentSchemeRepository.findAll().size() == 0) {
			this.iPaymentSchemeRepository
			.saveAll(
				List.of(
					PaymentScheme.builder().frequency(15).interestRate(13).numberOfPayments(5).paymentScheme("Scheme 1").build(),
					PaymentScheme.builder().frequency(15).interestRate(16).numberOfPayments(5).paymentScheme("Scheme 2").build()
				)
			);
		}
	}
}
