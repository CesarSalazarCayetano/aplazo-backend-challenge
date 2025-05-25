/**
 * 
 */
package com.aplazo.shopping.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 
 */
@RestController
@RequestMapping("/protected/customer")
public class CustomerController {
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/path")
	public String getMethodName() {
		System.out.println("Entro");
		return new String("Hello world");
	}
	
}
