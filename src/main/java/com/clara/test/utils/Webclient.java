package com.clara.test.utils;

import java.util.Collections;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

public class Webclient {
	
	public static WebClient getClient(String resourceUrl) {
		return WebClient.builder()
		  .baseUrl(resourceUrl)
		  //.defaultCookie("cookieKey", "cookieValue")
		  .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE) 
		  .defaultUriVariables(Collections.singletonMap("url", resourceUrl))
		  .build();
	}

}
