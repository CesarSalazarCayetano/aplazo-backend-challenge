/**
 * 
 */
package com.aplazo.shopping.model.dao;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import com.aplazo.shopping.enums.InstallmentStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
 * 
 */
@Entity
@Table(name = "payments")
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Payment {

	@Id
	@Column(name = "id_payment")
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID idPayment;
	
	@Min(0)
	private Double amount;
	
	@NotBlank(message = "the scheduled payment date cannot be empty")
	@Column(name = "scheduled_payment_date", nullable = false)
	private String scheduledPaymentDate;
	
	@Column(name = "payment_status")
	private InstallmentStatus paymentStatus;
	
	@Column(name = "created_at", updatable = false)
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	private LocalDateTime createdAt;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_loan")
	private Loan loan;
}
