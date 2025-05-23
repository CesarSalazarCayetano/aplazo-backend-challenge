/**
 * 
 */
package com.aplazo.shopping.model.dao;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author CesarSalazar
 */
@Entity
@Table(name = "payment_schemes")
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentScheme {

	@Id
	@Column(name = "id_payment_schemes")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idPaymentScheme;
	
	@Column(name = "payment_scheme", length = 20)
	private String paymentScheme;
	
	@Column(name = "number_of_payments", length = 2)
	private Integer numberOfPayments;
	
	@Column(name = "frecuency", length = 2)
	private Integer frequency;
	
	@Column(name = "interest_rate", length = 3)
	private Integer interestRate;
	
	@OneToOne(mappedBy = "paymentScheme")
	private Loan loan;
}
