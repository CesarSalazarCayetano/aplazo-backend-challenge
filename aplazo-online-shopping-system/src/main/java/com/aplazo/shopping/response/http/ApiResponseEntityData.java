/**
 * 
 */
package com.aplazo.shopping.response.http;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.aplazo.shopping.response.model.ApiResponse;

/**
 * Class to return in all application a single object.
 * Used when the result is success.
 * @author CesarSalazar 
 */
public class ApiResponseEntity {
	
	public static ResponseEntity<ApiResponse> responseEntitySuccess(String value, boolean success,
			HttpStatus httpCode, String message){
		return new ResponseEntity<ApiResponse>(new ApiResponse(value, success, httpCode.value(), message), httpCode);
	}
}
