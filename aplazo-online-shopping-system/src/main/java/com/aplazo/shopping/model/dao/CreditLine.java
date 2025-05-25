/**
 * 
 */
package com.aplazo.shopping.model.dao;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "credit_lines")
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreditLine {

	@Id
	@Column(name = "id_credit_line")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idCreditLine;
		
	@OneToOne
	@JoinColumn(name = "id_customer")
	private Customer customer;
	
	@ManyToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "id_credit_line_rule")
	private CreditLineRule creditLineRule;
	
	@OneToMany(mappedBy = "creditLine", fetch = FetchType.LAZY)
	private List<Loan> loan;
}
