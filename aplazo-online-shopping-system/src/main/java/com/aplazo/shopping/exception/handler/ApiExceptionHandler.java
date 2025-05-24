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

import io.github.resilience4j.ratelimiter.RequestNotPermitted;
import jakarta.servlet.http.HttpServletRequest;

/**
 * Class to handler the RunTimeExceptions.
 * @author CesarSalazar
 */
@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {
	
	/**
	 * Method to handler a customs error of the API.
	 * @param exception Recibe a object of type ApiException. 
	 * @param request Recive a object of type HttpServletRequest.
	 * @return return a custom object error.
	 */
	@ExceptionHandler(exception = ApiException.class)
	public ResponseEntity<ApiResponse> handlerApiException(ApiException exception, HttpServletRequest request){
		ErrorCode errorCode = exception.getErrorCode();
		ApiResponse apiResponse = 
				new ApiResponse(errorCode.getCode(), false, errorCode.getHttpCode().value(), request.getRequestURI(), errorCode.getMessage());
		return new ResponseEntity<ApiResponse>(apiResponse, errorCode.getHttpCode());
	}
	
	/**
	 *  Method to handler error of RateLimit by Resilience4j.
	 * @param exception Recive a object of type RequestNotPermitted.
	 * @param request Recive the object HttpServletRequest.
	 * @return return a custom object error.
	 */
	@ExceptionHandler(RequestNotPermitted.class)
	public ResponseEntity<ApiResponse> rateLimitError(RequestNotPermitted exception, HttpServletRequest request) {
		exception.printStackTrace();
		ErrorCode errorCode = ErrorCode.TOO_MANY_REQUEST;
		ApiResponse apiResponse = 
				new ApiResponse(errorCode.getCode(), false, errorCode.getHttpCode().value(), request.getRequestURI(), errorCode.getMessage());
		return new ResponseEntity<ApiResponse>(apiResponse, errorCode.getHttpCode());
	}
}
