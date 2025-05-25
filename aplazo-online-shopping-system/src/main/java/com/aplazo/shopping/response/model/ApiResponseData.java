/**
 * 
 */
package com.aplazo.shopping.response.model;

import org.springframework.http.ResponseEntity;

import lombok.Getter;
import lombok.Setter;

/**
 * @author CesarSalazar
 */
@Setter
@Getter
public class ApiResponseData<T> extends ApiResponse  {

	private T data;

	public ApiResponseData() {
		super();
	}

	public ApiResponseData(String value, boolean success, int httpCode, String message, T data) {
		super(value, success, httpCode, message);
		this.data = data;
	}

}
