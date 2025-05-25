/**
 * 
 */
package com.aplazo.shopping.security.config.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

/**
 * @author CesarSalazar
 */
@Setter
@Getter
public class LoginRequest {
	
	@NotBlank(message = "The email cannot be empty.")
	private String email;
	
	@Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$", 
		message = "The password is not valid. At least 8 characters. "
			+ "At least a number. "
			+ "At least a char upper. "
			+ "At least a char lower. "
			+ "At least a special character (#?!@$%^&*-).")
	private String password;

	public LoginRequest() {
		super();
		// TODO Auto-generated constructor stub
	}

	public LoginRequest(@NotBlank(message = "The email cannot be empty.") String email,
			@NotBlank(message = "The password cannot be empty.") @Min(value = 8, message = "At least 8 characters") @Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$", message = "The password is not valid. At least a number. At least a char upper. At least a char lower. At least a special character (#?!@$%^&*-).") String password) {
		super();
		this.email = email;
		this.password = password;
	}
	
	@Override
	public String toString() {
		return "LoginRequest [email=" + email + ", password=" + password + "]";
	}
	
}
