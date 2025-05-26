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
	UNATHORIZED("APZ000007","UNATHORIZED", HttpStatus.UNAUTHORIZED),
	
	BAD_AGE("APZ000009","BAD_AGE", HttpStatus.BAD_REQUEST),
	
	// CUSTOM ERRRORS
	
	USER_NOT_FOUND("APZ000101","USER_NOT_FOUND", HttpStatus.NOT_FOUND),
	USER_ALREADY_EXIST("APZ000102","USER_ALREADY_EXIST", HttpStatus.BAD_REQUEST),
	BAD_USER_CREDENTIALS("APZ000103","BAD_USER_CREDENTIALS", HttpStatus.BAD_REQUEST),
	CUSTOMER_ALREADY_EXIST("APZ000104","CUSTOMER_ALREADY_EXIST", HttpStatus.BAD_REQUEST),
	PAYMENT_SCHEME_NOT_FOUND("APZ000105","PAYMENT_SCHEME_NOT_FOUND", HttpStatus.NOT_FOUND),
	
	CREDIT_LINE_EXCEEDED("APZ000105","CREDIT_LINE_EXCEEDED", HttpStatus.PAYMENT_REQUIRED),
	
	VALUES_NOT_VALID("APZ000201","VALUES_NOT_VALID", HttpStatus.BAD_REQUEST),
	EMAIL_NOT_VALID("APZ000202","EMAIL_NOT_VALID", HttpStatus.BAD_REQUEST),
	
	// TODO: If i want to extend more the errors send this type on another enum
	OBJECT_MAPPER_VALIDATIONS("APZ000201","Error: on validate fields.", HttpStatus.INTERNAL_SERVER_ERROR);
	;
	
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
