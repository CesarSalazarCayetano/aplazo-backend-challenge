/**
 * 
 */
package com.aplazo.shopping.http;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import com.aplazo.shopping.response.model.ApiResponseData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author CesarSalazar
 */
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class UserIntegrationTest {
	private static final String CONTEXT_PATH = "/api/v1";
	private static final String USER_PATH = "/public/user";
	private static final String BASE_API = "http://localhost:";
	
	@LocalServerPort
	private int port;
	@Autowired
	private TestRestTemplate restTemplate;
	
	@DisplayName("The call at endpoint login must be successfull when send a valid body.")
	@Test
	void shouldReturn200WhenLoginSuccess() throws JsonProcessingException {
		var urlApi = BASE_API + this.port + CONTEXT_PATH + USER_PATH + "/login";
		
		HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.APPLICATION_JSON);
		
		Map<String, String> bodyParamMap = new HashMap<String, String>();

		bodyParamMap.put("email", "admin@admin.com");
		bodyParamMap.put("password", "Admin@25");
		
		String reqBodyData = new ObjectMapper().writeValueAsString(bodyParamMap);

		HttpEntity<String> requestEnty = new HttpEntity<>(reqBodyData, header);
		
		ApiResponseData<?> response = this.restTemplate.postForObject(urlApi, requestEnty, ApiResponseData.class);
		
		assertEquals(response.getHttpCode(), 200, "The http code must be 200");
	}
	
	@DisplayName("The call at endpoint login must have error if we send a not valid body.")
	@Test
	void shouldReturn400WhenLoginBodyDataAreInvalid() throws JsonProcessingException {
		var urlApi = BASE_API + this.port + CONTEXT_PATH + USER_PATH + "/login";
		
		HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.APPLICATION_JSON);
		
		Map<String, String> bodyParamMap = new HashMap<String, String>();

		bodyParamMap.put("email", "admin@admin.com");
		bodyParamMap.put("password", "A@25");
		
		String reqBodyData = new ObjectMapper().writeValueAsString(bodyParamMap);

		HttpEntity<String> requestEnty = new HttpEntity<>(reqBodyData, header);
		
		ApiResponseData<?> response = this.restTemplate.postForObject(urlApi, requestEnty, ApiResponseData.class);
		
		assertEquals(response.getHttpCode(), 400, "The http code must be 400");
	}
}
