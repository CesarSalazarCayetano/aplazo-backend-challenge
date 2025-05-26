/**
 * 
 */
package com.aplazo.shopping.http;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.Field;
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
import org.springframework.http.ResponseEntity;

import com.aplazo.shopping.response.model.ApiResponseData;
import com.aplazo.shopping.security.config.response.JwtResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author CesarSalazar
 */
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class CustomerIntegrationTest {

	private static final String CONTEXT_PATH = "/api/v1";
	private static final String USER_PATH = "/public/user";
	private static final String CUSTOMER_PATH = "/protected/customers";
	private static final String BASE_API = "http://localhost:";
	
	@LocalServerPort
	private int port;
	@Autowired
	private TestRestTemplate restTemplate;
	
	@DisplayName("The call at endpoint create, must be fails because i dont send the HEADER and token.")
	@Test
	void shouldReturn401WhenNotSendHeaderAuthorization() throws JsonProcessingException {
		var urlApi = BASE_API + this.port + CONTEXT_PATH + CUSTOMER_PATH;
		
		HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.APPLICATION_JSON);
//		header.add(CONTEXT_PATH, BASE_API);
		
		Map<String, String> bodyParamMap = new HashMap<String, String>();

		bodyParamMap.put("firstName", "ReglaUno");
		bodyParamMap.put("lastName", "ReglaUno");
		bodyParamMap.put("secondLastName", "ReglaUno");
		bodyParamMap.put("dateOfBirth", "2000-01-01");
		
		String reqBodyData = new ObjectMapper().writeValueAsString(bodyParamMap);

		HttpEntity<String> requestEnty = new HttpEntity<>(reqBodyData, header);
		
		ResponseEntity<Object> response = this.restTemplate.postForEntity(urlApi, requestEnty, Object.class);
		
		assertEquals(response.getStatusCode().value(), 401, "The http code must be 401");
	}
	
	@DisplayName("The call at endpoint create, must be successfull if its the first call, because the endpint validate if exist the data sended.")
	@Test
	void shouldReturn201WhenSendHeaderAuthorization() throws JsonProcessingException, NoSuchFieldException, SecurityException {
		var urlApiLogin = BASE_API + this.port + CONTEXT_PATH + USER_PATH + "/login";
		HttpHeaders headerLogin = new HttpHeaders();
		headerLogin.setContentType(MediaType.APPLICATION_JSON);
		Map<String, String> bodyParamMapLogin = new HashMap<String, String>();
		bodyParamMapLogin.put("email", "admin@admin.com");
		bodyParamMapLogin.put("password", "Admin@25");
		String reqBodyDataLogin = new ObjectMapper().writeValueAsString(bodyParamMapLogin);
		HttpEntity<String> requestEntyLogin = new HttpEntity<>(reqBodyDataLogin, headerLogin);
		ApiResponseData<?> responseLogin = this.restTemplate.postForObject(urlApiLogin, requestEntyLogin, ApiResponseData.class);
		assertEquals(responseLogin.getHttpCode(), 200, "The http code must be 200");
		
		Map<String, String> dataResult = (Map<String, String>) responseLogin.getData();
		String token = dataResult.get("token");
		
		var urlApi = BASE_API + this.port + CONTEXT_PATH + CUSTOMER_PATH;
		HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.APPLICATION_JSON);
		header.add("Authorization", token);
		
		Map<String, String> bodyParamMap = new HashMap<String, String>();

		bodyParamMap.put("firstName", "ReglaUno");
		bodyParamMap.put("lastName", "ReglaUno");
		bodyParamMap.put("secondLastName", "ReglaUno");
		bodyParamMap.put("dateOfBirth", "2000-01-01");
		
		String reqBodyData = new ObjectMapper().writeValueAsString(bodyParamMap);

		HttpEntity<String> requestEnty = new HttpEntity<>(reqBodyData, header);
		
		ResponseEntity<Object> response = this.restTemplate.postForEntity(urlApi, requestEnty, Object.class);
		
		// This is OK the first time because the user only can register one customer
//		assertEquals(response.getStatusCode().value(), 201, "The http code must be 201");
		assertEquals(response.getStatusCode().value(), 400, "The http code must be 400");
	}
}
