/**
 * 
 */
package com.aplazo.shopping.components.schemas.response;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @author CesarSalazar
 */
public record CustomerResponse(UUID idCustomer, Double creditLineAmount, Double availableCreditLineAmount, LocalDateTime createdAt) {
	
}
