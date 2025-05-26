/**
 * 
 */
package com.aplazo.shopping.components.schemas.response;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * 
 */
public record InstallmentResponse(UUID id, Double amount,  String scheduledPaymentDate, LocalDateTime createdAt) {

}
