/**
 * 
 */
package com.aplazo.shopping.components.schemas.request;

import java.util.UUID;

import org.hibernate.validator.constraints.Range;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * @author CesarSalazar
 */
@Setter
@Getter
public class LoanRequest {

	@NotNull(message = "The id cannot be empty.")
	private UUID idCustomer;
	
	@Min(0)
	@Range(min = 0, max = 8000, message = "Enter a valid amount")
    private Double amount;
}
