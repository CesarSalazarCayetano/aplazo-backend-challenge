/**
 * 
 */
package com.aplazo.shopping.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aplazo.shopping.enums.ErrorCode;
import com.aplazo.shopping.exception.ApiException;

import io.github.resilience4j.ratelimiter.annotation.RateLimiter;

/**
 * @author CesarSalazar
 */
@RestController
@RequestMapping("/public/user")
public class UserController {
	
	@RateLimiter(name = "public-api")
	@GetMapping("/test")
	public ResponseEntity<?> getMethodName() {
		if(true) {
			throw new ApiException(ErrorCode.INTERNAL_SERVER_ERROR);
		}
		return ResponseEntity.ok("hello world");
	}
	
}
