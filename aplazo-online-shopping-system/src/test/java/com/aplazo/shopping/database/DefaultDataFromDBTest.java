/**
 * 
 */
package com.aplazo.shopping.database;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.aplazo.shopping.repository.ICreditLineRuleRepository;
import com.aplazo.shopping.repository.IPaymentSchemeRepository;

/**
 * Class to test the default data in the tables of the DB.
 * @author CesarSalazar
 */
@Testcontainers
@SpringBootTest
public class DefaultDataFromDBTest {

	@Container
	static PostgreSQLContainer<?> postgreContainer = new PostgreSQLContainer<>("postgres:latest");
	
	@BeforeAll
	static void beforeAllStartPostgreSQL() {
		postgreContainer.start();
	}
	@AfterAll
	static void afterAllStopPostgreSQL() {
		postgreContainer.stop();
	}
	@DynamicPropertySource
	static void configurePostgreSQLProps(DynamicPropertyRegistry registry) {
		registry.add("spring.datasource.url", postgreContainer::getJdbcUrl);
		registry.add("spring.datasource.username", postgreContainer::getUsername);
		registry.add("spring.datasource.password", postgreContainer::getPassword);
	}
	
	@Autowired
	private ICreditLineRuleRepository iCreditLineRuleRepository;
	
	@Autowired
	private IPaymentSchemeRepository iPaymentSchemeRepository;
	
	@DisplayName("When execute a select from a credit_line_rules, we should to get almost 4 rules that has been declared on the requirements.")
	@Test
	void shouldReturnAllDataFromCreditLineRule() {
		int defaultSizeListRules = 4;
		int sizeListRules = this.iCreditLineRuleRepository.findAll().size();
		Assertions.assertEquals(defaultSizeListRules, sizeListRules, "The size of the expected result has not equals to default.");
	}
	
	@DisplayName("When execute a select from a payment_schemes, we should to get almost 2 schemes that has been declared on the requirements.")
	@Test
	void shouldReturnAllDataFromPaymentScheme() {
		int defaultPaymentScheme = 2;
		int sizeListPaymentScheme = this.iPaymentSchemeRepository.findAll().size();
		Assertions.assertEquals(defaultPaymentScheme, sizeListPaymentScheme, "The size of the expected result has not equals to default.");
	}
}
