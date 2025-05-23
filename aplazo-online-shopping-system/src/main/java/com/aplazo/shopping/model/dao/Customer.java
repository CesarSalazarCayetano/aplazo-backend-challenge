/**
 * 
 */
package com.aplazo.shopping.model.dao;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
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
@Table(name = "customers")
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Customer {

	@Id
	@Column(name = "id_customer")
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID idCustomer;
	
	@NotBlank(message = "The field first name cannot be empty")
	@Column(name = "first_name", nullable = false, length = 50)
	private String firstName;
	
	@NotBlank(message = "The field last name cannot be empty")
	@Column(name = "last_name", nullable = false, length = 50)
	private String lastName;

	@NotBlank(message = "The field second last name cannot be empty")
	@Column(name = "second_last_name", nullable = false, length = 50)
	private String secondLastName;
	
	@NotBlank(message = "The field date of birth cannot be empty")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Column(name = "date_of_birth", nullable = false)
	private Date dateOfBirth;
	
	@Column(name = "created_at")
	@Temporal(TemporalType.TIMESTAMP)
	private LocalDateTime createdAt; 
	
	@OneToOne(mappedBy = "customer", cascade = CascadeType.PERSIST)
	private CreditLine creditLine;
	
	@OneToOne
	@JoinColumn(name = "id_user")
	private User user;
}
