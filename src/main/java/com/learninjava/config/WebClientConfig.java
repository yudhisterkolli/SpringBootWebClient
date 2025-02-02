package com.learninjava.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Configuration - Creates a WebClient
 * 
 * @author learninjava.com
 *
 */
@Configuration
public class WebClientConfig {

	@Bean(name = "EmployeeClient")
	public WebClient createWebClient() {
		return WebClient.builder().baseUrl("http://127.0.0.1:8000/employee")
				.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
				.build();
	}

	@Bean(name = "PostsWebClient")
	public WebClient postsWebClient() {
		return WebClient.builder().baseUrl("https://jsonplaceholder.typicode.com")
				.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
				.build();
	}
}
