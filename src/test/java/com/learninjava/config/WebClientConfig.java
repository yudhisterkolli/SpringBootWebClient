package com.learninjava.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Configuration - Creates a WebClient with mock URL
 * 
 * @author learninjava.com
 *
 */
@Configuration
public class WebClientConfig {

	@Bean
	public WebClient createWebClient() {
		return WebClient.builder().baseUrl("http://localhost:9090")
				.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
				.build();
	}
}
