/**
 * 
 */
package com.aplazo.shopping.response.model;

import java.time.Instant;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;

/**
 * Class to return a unique object in all endpoint.
 * @author CesarSalazar
 */
@Getter
@Setter
@JsonInclude(Include.NON_NULL)
public class ApiResponse {

	private String value;
	private String code;
	private boolean success;
	private int httpCode;
	private Long timeStamp;
	private String path;
	private String message;
	private List<String> errorFields;
	
	public ApiResponse() {
		super();
	}

	/**
	 * Constructor for return an error
	 * @param code
	 * @param success
	 * @param httpCode
	 * @param timeStamp
	 * @param path
	 * @param message
	 */
	public ApiResponse(String code, boolean success, int httpCode, String path, String message, List<String> errorFields) {
		super();
		this.code = code;
		this.success = success;
		this.httpCode = httpCode;
		this.timeStamp = this.getTimeStamp();
		this.path = path;
		this.message = message;
		this.errorFields = errorFields;
	}

	/*
	 * Constructor for return a success
	 */
	public ApiResponse(String value, boolean success, int httpCode, String message) {
		super();
		this.value = value;
		this.success = success;
		this.httpCode = httpCode;
		this.message = message;
	}
	
	/**
	 * Get the current Timestamp in seconds, to asignate on each construnctor calls.
	 * @return
	 */
	private Long getTimeStamp() {
		return Instant.now().getEpochSecond();
	}
	
}
