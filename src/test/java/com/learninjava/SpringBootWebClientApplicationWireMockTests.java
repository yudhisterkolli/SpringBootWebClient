package com.learninjava;

import static com.github.tomakehurst.wiremock.client.WireMock.delete;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.equalToJson;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.put;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.learninjava.model.Post;

/**
 * Integration Testing - Approach 4 - Using WireMockServer
 * 
 * @author learninjava.com
 *
 */
@SpringBootTest()
@ExtendWith(SpringExtension.class)
class SpringBootWebClientApplicationWireMockTests {

	private WireMockServer wireMockServer;

	private WebClient webClient;

	@BeforeEach
	public void setup() {
		wireMockServer = new WireMockServer(WireMockConfiguration.wireMockConfig().dynamicPort());
		wireMockServer.start();
		webClient = WebClient.builder().baseUrl(wireMockServer.baseUrl()).build();
	}

	@AfterEach
	void tearDown() throws Exception {
		wireMockServer.stop();
	}

	@Test
	public void test_getPost() throws Exception {

		JsonNode jsonNode = new ObjectMapper().readTree("{" + 
				"    \"userId\": 1001," + 
				"    \"id\": 1001," + 
				"    \"title\": \"Some title\"," + 
				"    \"body\": \"Some body\"" + 
				"}");

		wireMockServer.stubFor(get("/post/1").willReturn(
				WireMock.aResponse()
						.withStatus(HttpStatus.OK.value())
						.withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
						.withJsonBody(jsonNode)
				)
		);

		Post response = webClient.get()
			.uri("/post/{id}", 1)
			.accept(MediaType.APPLICATION_JSON)
			.retrieve()
			.bodyToMono(Post.class)
			.block();
		
		Assertions.assertEquals(1001, response.getId());
	}
	
	@Test
	public void test_createPost() throws Exception {

		JsonNode jsonNode = new ObjectMapper().readTree("{" + 
				"    \"userId\": 1002," + 
				"    \"id\": 1002," + 
				"    \"title\": \"Some title\"," + 
				"    \"body\": \"Some body\"" + 
				"}");
		
		wireMockServer.stubFor(post("/post")
				.withHeader(HttpHeaders.ACCEPT, equalTo(MediaType.APPLICATION_JSON_VALUE))
				.withRequestBody(equalToJson("{" + 
						"    \"userId\": 1002," + 
						"    \"id\": 1002," + 
						"    \"title\": \"Some title\"," + 
						"    \"body\": \"Some body\"" + 
						"}"))
				.willReturn(
					WireMock.aResponse()
							.withStatus(HttpStatus.OK.value())
							.withHeader(HttpHeaders.CONTENT_TYPE, 
                  MediaType.APPLICATION_JSON_VALUE)
							.withJsonBody(jsonNode)
				));

		Post post = new Post(1002, 1002, "Some title", "Some body");

		Post response = webClient.post()
			.uri("/post")
			.accept(MediaType.APPLICATION_JSON)
			.body(BodyInserters.fromValue(post))
			.retrieve()
			.bodyToMono(Post.class)
			.block();
		
		Assertions.assertEquals(1002, response.getId());
	}	
	
	@Test
	public void test_updatePost() throws Exception {
		
		JsonNode jsonNode = new ObjectMapper().readTree("{" + 
				"    \"userId\": 1003," + 
				"    \"id\": 1003," + 
				"    \"title\": \"Some title\"," + 
				"    \"body\": \"Some body\"" + 
				"}");		

		wireMockServer.stubFor(put("/post")
				.withHeader(HttpHeaders.ACCEPT, equalTo(MediaType.APPLICATION_JSON_VALUE))
				.withRequestBody(equalToJson("{" + 
						"    \"userId\": 1003," + 
						"    \"id\": 1003," + 
						"    \"title\": \"Some title\"," + 
						"    \"body\": \"Some body\"" + 
						"}"))
				.willReturn(
					WireMock.aResponse()
							.withStatus(HttpStatus.OK.value())
							.withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
							.withJsonBody(jsonNode)));

		Post post = new Post(1003, 1003, "Some title", "Some body");

		Post response = webClient.put()
			.uri("/post")
			.accept(MediaType.APPLICATION_JSON)
			.body(BodyInserters.fromValue(post))
			.retrieve()
			.bodyToMono(Post.class)
			.block();
		
		Assertions.assertEquals(1003, response.getId());
	}	

	@Test
	public void test_deletePost() throws Exception {
		
		JsonNode jsonNode = new ObjectMapper().readTree("{" + 
				"    \"userId\": 1004," + 
				"    \"id\": 1004," + 
				"    \"title\": \"Some title\"," + 
				"    \"body\": \"Some body\"" + 
				"}");		

		wireMockServer.stubFor(delete("/post/1").willReturn(
				WireMock.aResponse()
						.withStatus(HttpStatus.OK.value())
						.withHeader("Content-Type", "application/json")
						.withJsonBody(jsonNode)
				)
		);

		Post response = webClient.delete()
			.uri("/post/{id}", 1)
			.accept(MediaType.APPLICATION_JSON)
			.retrieve()
			.bodyToMono(Post.class)
			.block();
		
		Assertions.assertEquals(1004, response.getId());
	}	
}
