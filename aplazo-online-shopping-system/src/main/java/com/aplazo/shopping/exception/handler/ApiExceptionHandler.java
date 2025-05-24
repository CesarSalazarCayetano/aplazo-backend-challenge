/**
 * 
 */
package com.aplazo.shopping.exception.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.aplazo.shopping.enums.ErrorCode;
import com.aplazo.shopping.exception.ApiException;
import com.aplazo.shopping.response.model.ApiResponse;

import jakarta.servlet.http.HttpServletRequest;

/**
 * Class to handler the RunTimeExceptions.
 * @author CesarSalazar
 */
@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(exception = ApiException.class)
	public ResponseEntity<ApiResponse> handlerApiException(ApiException exception, HttpServletRequest request){
		ErrorCode errorCode = exception.getErrorCode();
		ApiResponse apiResponse = 
				new ApiResponse(errorCode.getCode(), false, errorCode.getHttpCode().value(), request.getRequestURI(), errorCode.getMessage());
		return new ResponseEntity<ApiResponse>(apiResponse, errorCode.getHttpCode());
	}
}
