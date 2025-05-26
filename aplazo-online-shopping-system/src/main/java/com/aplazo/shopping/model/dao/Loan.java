/**
 * 
 */
package com.aplazo.shopping.model.dao;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import com.aplazo.shopping.enums.LoanStatus;

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
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author CesarSalazar
 */
@Entity
@Table(name = "loans")
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Loan {

	@Id
	@Column(name = "id_loan")
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID idLoan;
	
	@Min(0)
	@Column(name = "purchase_commission")
	private Double purchaseCommission;
	
	@Min(0)
	@Column(name = "amount_loan")
	private Double amountLoan;
	
	@Min(0)
	@Column(name = "total_amount")
	private Double totalAmount;

	@Min(0)
	@Column(name = "amount_by_schedule")
	private Double amountBySchedule;

	@Column(name = "loan_status")
	private LoanStatus loanStatus;
	
	@NotBlank(message = "The field date schedule payment cannot be empty.")
	@Column(name = "date_schedule_payment")
	private String dateSchedulePayment;
	
	@Column(name = "created_at", updatable = false)
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	private LocalDateTime createdAt;
	
	@ManyToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "id_credit_line")
	private CreditLine creditLine;
	
	@ManyToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "id_payment_scheme")
	private PaymentScheme paymentScheme;
	
	@OneToMany(mappedBy = "loan", fetch = FetchType.EAGER)
	private List<Payment> payment;
}
