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
import jakarta.persistence.Table;
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
@Table(name = "users_role")
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserRole {

	@Id
	@Column(name = "id_user_role")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idUserRole;
	
	@Column(name = "user_role", length = 10, unique = true)
	@NotBlank(message = "The field user_role cannot be empty")
	private String userRole;
	
	@OneToMany(mappedBy = "userRole", fetch = FetchType.LAZY)
	private List<User> user;
}
