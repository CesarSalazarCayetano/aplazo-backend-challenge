/**
 * 
 */
package com.aplazo.shopping.handler.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import io.github.resilience4j.ratelimiter.RequestNotPermitted;

/**
 * @author CesarSalazar
 */
@RestControllerAdvice
public class RateLimitExceptionHandler {
	@ExceptionHandler(RequestNotPermitted.class)
	public ResponseEntity<?> rateLimitError(RequestNotPermitted exception) {
		exception.printStackTrace();
		return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body("The rate limit permited has exceeded, try later.");
	}
}
