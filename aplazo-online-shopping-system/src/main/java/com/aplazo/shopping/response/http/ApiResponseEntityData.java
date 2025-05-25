/**
 * 
 */
package com.aplazo.shopping.response.http;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.aplazo.shopping.response.model.ApiResponseData;

/**
 * Class to return in all application a single object.
 * Used when the result is success.
 * @author CesarSalazar 
 */
public class ApiResponseEntityData<T> {
	
	public ResponseEntity<?> responseEntitySuccessData(String value, HttpStatus httpCode, String message, T data){
		return new ResponseEntity<ApiResponseData<T>>(new ApiResponseData<T>(value, true, httpCode.value(), message, data), httpCode);
	}
}
