package com.example.PP_3_1_5;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;



@SpringBootApplication
public class Application {

	public static void main(String[] args) {

		RestTemplate restTemplate = new RestTemplate();
		String URL = "http://94.198.50.185:7081/api/users";
		String response = restTemplate.getForObject(URL, String.class);
		System.out.println("Response: " + response);
		ResponseEntity<String> responseEntity = restTemplate.getForEntity(URL, String.class);
		String sessionId = null;
		if (responseEntity.getHeaders().containsKey("Set-Cookie")) {
			for (String cookie : responseEntity.getHeaders().get("Set-Cookie")) {
				sessionId = cookie;
			}
		}
		String jsonBodyForPOST = "{\"id\":3,\"name\":\"James\",\"lastName\":\"Brown\",\"age\":33}";
		String jsonBodyForPUT = "{\"id\":3,\"name\":\"Thomas\",\"lastName\":\"Shelby\",\"age\":33}";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Cookie", sessionId);
		HttpEntity<String> requestEntityForPOST = new HttpEntity<>(jsonBodyForPOST, headers);
		ResponseEntity<String> responseEntityForPOST = restTemplate.postForEntity(URL, requestEntityForPOST, String.class);
		HttpEntity<String> requestEntityForPUT = new HttpEntity<>(jsonBodyForPUT, headers);
		ResponseEntity<String> responseEntityForPUT = restTemplate.exchange(URL, HttpMethod.PUT, requestEntityForPUT, String.class);
		HttpEntity<String> requestEntityForDELETE = new HttpEntity<>(headers);
		ResponseEntity<String> responseEntityForDELETE = restTemplate.exchange(URL+"/3", HttpMethod.DELETE, requestEntityForDELETE, String.class);
		System.out.println(responseEntityForPOST.getBody() + responseEntityForPUT.getBody() + responseEntityForDELETE.getBody());

	}

}