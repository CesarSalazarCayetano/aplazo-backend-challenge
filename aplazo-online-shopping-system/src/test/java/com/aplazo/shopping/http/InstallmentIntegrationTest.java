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
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author CesarSalazar
 */
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class InstallmentIntegrationTest {

	@LocalServerPort
	private int port;
	@Autowired
	private TestRestTemplate restTemplate;
	
	private static final String CONTEXT_PATH = "/api/v1";
	private static final String INSTALLMENT_PATH = "/protected/installments";
	private static final String BASE_API = "http://localhost:";
	
	@DisplayName("The call at endpoint create, must be fails because i dont send the HEADER and token.")
	@Test
	void shouldReturn401WhenNotSendHeaderAuthorization() throws JsonProcessingException {
		var urlApi = BASE_API + this.port + CONTEXT_PATH + INSTALLMENT_PATH;
		
		HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.APPLICATION_JSON);
//		header.add(CONTEXT_PATH, BASE_API);
		
		Map<String, Object> bodyParamMap = new HashMap<String, Object>();

		bodyParamMap.put("idLoan", "48bf414d-5e29-49e0-ba36-2c9f5a246db2");
		bodyParamMap.put("amount", 232.00D);
		
		String reqBodyData = new ObjectMapper().writeValueAsString(bodyParamMap);

		HttpEntity<String> requestEnty = new HttpEntity<>(reqBodyData, header);
		
		ResponseEntity<Object> response = this.restTemplate.postForEntity(urlApi, requestEnty, Object.class);
		
		assertEquals(response.getStatusCode().value(), 401, "The http code must be 401");
	}
}
