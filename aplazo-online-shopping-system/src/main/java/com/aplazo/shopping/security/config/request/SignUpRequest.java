/**
 * 
 */
package com.aplazo.shopping.security.config.request;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

/**
 * @author CesarSalazar
 */
@Setter
@Getter
public class SignUpRequest extends LoginRequest {

	@NotBlank(message = "The first name cannot be empty.")
	private String firstName;
	
	@NotBlank(message = "The last name cannot be empty.")
	private String lastName;

	@NotBlank(message = "The second last name cannot be empty.")
	private String secondLastName;
	
	@JsonFormat(shape = Shape.STRING, pattern = "dd/MM/yyyy")
	private Date dateOfBirth;

	public SignUpRequest() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SignUpRequest(String email, String password) {
		super(email, password);
		// TODO Auto-generated constructor stub
	}

	public SignUpRequest(@NotBlank(message = "The first name cannot be empty.") String firstName,
			@NotBlank(message = "The last name cannot be empty.") String lastName,
			@NotBlank(message = "The second last name cannot be empty.") String secondLastName, Date dateOfBirth) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.secondLastName = secondLastName;
		this.dateOfBirth = dateOfBirth;
	}

	@Override
	public String toString() {
		return "SignUpRequest [firstName=" + firstName + ", lastName=" + lastName + ", secondLastName=" + secondLastName
				+ ", dateOfBirth=" + dateOfBirth + "]";
	}
	
}
