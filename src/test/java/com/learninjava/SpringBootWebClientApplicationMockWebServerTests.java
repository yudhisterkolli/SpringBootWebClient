package com.learninjava;

import java.io.IOException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.learninjava.model.Post;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import reactor.core.publisher.Mono;


/** Unit Testing - Approach 2 - Using MockWebServer
 *  Need to define WebClient bean
 * 
 * @author learninjava.com
 *
 */
@SpringBootTest()
@ExtendWith(SpringExtension.class)
class SpringBootWebClientApplicationMockWebServerTests {

	private static MockWebServer mockWebServer;
	
	@Autowired
	WebClient webclient;
	
	@Autowired
	private SpringBootWebClientController controller;

	@BeforeAll
	public static void setup() throws IOException {

		mockWebServer = new MockWebServer();
		mockWebServer.start(9090);
	}

	@AfterAll
	public static void tearDown() throws IOException {

		mockWebServer.shutdown();
	}

	@Test
	public void test_getPost() throws Exception {
		
		Post post = new Post(1001, 1001, "Some title", "Some body");

		mockWebServer.enqueue(new MockResponse()
				.setBody(new ObjectMapper().writeValueAsString(post))
				.addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
				);
		
		ResponseEntity<Mono<Post>> resonse = controller.getPost("1");
		Assertions.assertEquals(1001, resonse.getBody().block().getId());
	}
	
	@Test
	public void test_createPost() throws Exception {
		
		Post post = new Post(1002, 1002, "Some title", "Some body");

		mockWebServer.enqueue(new MockResponse()
				.setBody(new ObjectMapper().writeValueAsString(post))
				.addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
				);
		
		Post resonse = controller.createPost(post).block();
		// Check userId instead of id as id will be auto generated and sent back by the API
		Assertions.assertEquals(1002, resonse.getUserId());
	}
	
	@Test
	public void test_updatePost() throws Exception {
		
		Post post = new Post(1003, 1003, "Some title", "Some body");

		mockWebServer.enqueue(new MockResponse()
				.setBody(new ObjectMapper().writeValueAsString(post))
				.addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
				);
		
		Post resonse = controller.updatePost(post).block();
	  // Check userId instead of id as id will be auto generated and sent back by the API
		Assertions.assertEquals(1003, resonse.getUserId());
	}
	
	@Test
	public void test_deletePost() throws Exception {
		
		Post post = new Post(1004, 1004, "Some title", "Some body"); 
		
		mockWebServer.enqueue(new MockResponse()
				.setBody(new ObjectMapper().writeValueAsString(post))
				.addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
				);
		
		Mono<Post> resonse = controller.deletePost("1");
		Assertions.assertEquals(1004, resonse.block().getId());
	}
}
