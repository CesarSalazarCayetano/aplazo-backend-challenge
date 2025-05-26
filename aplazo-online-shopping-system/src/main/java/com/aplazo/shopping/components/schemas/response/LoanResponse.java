/**
 * 
 */
package com.aplazo.shopping.components.schemas.response;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @author CesarSalazar
 */
public record LoanResponse(UUID id, UUID customerId, Double amount, String paymentSchedule, LocalDateTime createdAt) {

}
