/**
 * 
 */
package com.aplazo.shopping.exception;

import java.util.List;

import com.aplazo.shopping.enums.ErrorCode;

/**
 * Class to throw custom exceptions for the API.
 * @author CesarSalazar
 */
public class ApiException extends RuntimeException {

	private static final long serialVersionUID = 2608618057688348345L;
	private final ErrorCode errorCode;
	private List<String> errorFields;

	public ApiException(ErrorCode errorCode, List<String> errorFields) {
		super();
		this.errorCode = errorCode;
		this.errorFields = errorFields;
	}

	public ApiException(ErrorCode errorCode) {
		super();
		this.errorCode = errorCode;
	}

	public ErrorCode getErrorCode() {
		return errorCode;
	}

	public List<String> getErrorFields() {
		return errorFields;
	}
	
	
}
