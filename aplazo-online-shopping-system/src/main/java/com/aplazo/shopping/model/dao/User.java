/**
 * 
 */
package com.aplazo.shopping.model.dao;

import java.time.LocalDateTime;

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
@Table(name = "users")
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class User {

	@Id
	@Column(name = "id_user")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idUser;
	
	@Column(length = 100)
	@NotBlank(message = "The field email cannot be empty")
	private String email;
	
	@Column(length = 24)
	@NotBlank(message = "The field password cannot be empty")
	private String password;
	
	@OneToOne(mappedBy = "user", cascade = CascadeType.PERSIST)
	private Customer customer;
}
