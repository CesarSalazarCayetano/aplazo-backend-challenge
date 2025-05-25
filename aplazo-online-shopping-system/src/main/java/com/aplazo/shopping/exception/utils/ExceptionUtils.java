/**
 * 
 */
package com.aplazo.shopping.exception.utils;

import java.util.ArrayList;
import java.util.List;

import org.springframework.validation.BindingResult;

/**
 * @author CesarSalazar
 */
public class ExceptionUtils {

	private ExceptionUtils() {}
	
	public static List<String> getErrorsFromBindingResult(BindingResult bindingResult) {
		List<String> errors = new ArrayList<>(); 
		bindingResult.getFieldErrors().stream()
				.forEach(fieldError -> {
					errors.add(fieldError.getField() + ": " + fieldError.getDefaultMessage());
				});
		return errors.stream().sorted().toList();
	}
}
