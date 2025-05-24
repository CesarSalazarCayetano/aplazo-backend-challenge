/**
 * 
 */
package com.aplazo.shopping.enums;

import org.springframework.http.HttpStatus;

/**
 * Enum to centralice all error codes, messages and http status.
 * @author CesarSalazar
 */
public enum ErrorCode {

	INVALID_CUSTOMER_REQUEST("APZ000002","INVALID_CUSTOMER_REQUEST", HttpStatus.BAD_REQUEST),
	CUSTOMER_NOT_FOUND("APZ000005","CUSTOMER_NOT_FOUND", HttpStatus.NOT_FOUND),
	
	TOO_MANY_REQUEST("APZ000003","RATE_LIMIT_ERROR", HttpStatus.TOO_MANY_REQUESTS),
	
	INVALID_LOAN_REQUEST("APZ000006","INVALID_LOAN_REQUEST", HttpStatus.BAD_REQUEST),
	LOAN_NOT_FOUND("APZ000008","LOAN_NOT_FOUND", HttpStatus.NOT_FOUND),
	
	INTERNAL_SERVER_ERROR("APZ000001","INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR),
	GENERAL_INVALID_REQUEST("APZ000004","INVALID_REQUEST", HttpStatus.BAD_REQUEST),
	UNATHORIZED("APZ000007","UNATHORIZED", HttpStatus.UNAUTHORIZED);
	
	private String code;
	private String message;
	private HttpStatus httpCode;
	
	private ErrorCode(String code, String message, HttpStatus httpCode) {
		this.code = code;
		this.message = message;
		this.httpCode = httpCode;
	}
	public String getCode() {
		return code;
	}
	public String getMessage() {
		return message;
	}
	public HttpStatus getHttpCode() {
		return httpCode;
	}
	
}
