/**
 * 
 */
package com.aplazo.shopping.model.dao;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author CesarSalazar
 */
@Entity
@Table(name = "credit_line_rules")
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreditLineRule {

	@Id
	@Column(name = "id_credit_line_rule")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idCreditRule;
	
	@Column(name = "rule_name", length = 6)
	private String ruleName;
	
	@Column(name = "rule_description", length = 255)
	private String ruleDescription;
	
	@Min(0)
	@Column(name = "credit_line_amount", length = 5)
	private Double creditLineAmount;
	
	@Column(name = "age_range", length = 7)
	private String ageRange;
	
	@OneToMany(mappedBy = "creditLineRule", fetch = FetchType.LAZY)
	private List<CreditLine> creditLine;
	
}
