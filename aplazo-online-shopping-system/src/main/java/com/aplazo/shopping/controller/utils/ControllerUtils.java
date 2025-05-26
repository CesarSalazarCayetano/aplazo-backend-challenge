/**
 * 
 */
package com.aplazo.shopping.controller.utils;

import java.util.List;

import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.validation.BindingResult;

import com.aplazo.shopping.enums.ErrorCode;
import com.aplazo.shopping.exception.ApiException;
import com.aplazo.shopping.exception.utils.ExceptionUtils;

/**
 * @author CesarSalazar
 */
public class ControllerUtils {

	public static void validateFields(BindingResult bindingResult, String email) {
		if(bindingResult.hasErrors()) {
			List<String> errors = ExceptionUtils.getErrorsFromBindingResult(bindingResult);
			throw new ApiException(ErrorCode.VALUES_NOT_VALID, errors);
		}
		if(!EmailValidator.getInstance().isValid(email)) {
			throw new ApiException(ErrorCode.EMAIL_NOT_VALID);
		}
	}
}
