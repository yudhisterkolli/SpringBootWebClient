package com.learninjava;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.reactive.function.client.WebClient;

import com.learninjava.model.Post;

import reactor.core.publisher.Mono;

/**
 * Unit Testing - Approach 1 - Using Mockito
 * 
 * @author learninjava.com
 *
 */
@SpringBootTest()
@ExtendWith(SpringExtension.class)
class SpringBootWebClientApplicationMockitoTests {

	@Mock
	private WebClient webClientMock;

	@Mock
	private WebClient.RequestBodyUriSpec requestBodyUriSpecMock;

	@Mock
	private WebClient.RequestBodySpec requestBodySpecMock;

	@SuppressWarnings("rawtypes")
	@Mock
	private WebClient.RequestHeadersSpec requestHeadersSpecMock;
	
	@SuppressWarnings("rawtypes")
	@Mock
	private WebClient.RequestHeadersUriSpec requestHeadersUriSpecMock;	

	@Mock
	private WebClient.ResponseSpec responseSpecMock;

	@Mock
	private Mono<Post> postResponseMock;

	@InjectMocks
	private SpringBootWebClientController controllerMock;

	@SuppressWarnings("unchecked")
	@Test
	public void test_getPost() throws Exception {
		
		Post post = new Post(1001, 1001, "Some title", "Some body");
		
		when(webClientMock.get()).thenReturn(requestHeadersUriSpecMock);
		when(requestHeadersUriSpecMock.uri(anyString())).thenReturn(requestHeadersSpecMock);
		when(requestHeadersSpecMock.retrieve()).thenReturn(responseSpecMock);
		when(responseSpecMock.bodyToMono(ArgumentMatchers.<Class<Post>>notNull())).thenReturn(Mono.just(post));

		Mono<Post> response = controllerMock.getPost("1").getBody();
		Assertions.assertEquals(1001, response.block().getId());
		Assertions.assertEquals("Some title", response.block().getTitle());
	}	
	
	@SuppressWarnings("unchecked")
	@Test
	public void test_createPost() throws Exception {

		Post post = new Post(1002, 1002, "Some title", "Some body");

		when(webClientMock.post()).thenReturn(requestBodyUriSpecMock);
		when(requestBodyUriSpecMock.uri(anyString())).thenReturn(requestBodySpecMock);
		when(requestBodySpecMock.header(any(), any())).thenReturn(requestBodySpecMock);
		when(requestBodySpecMock.body(any())).thenReturn(requestHeadersSpecMock);
		when(requestHeadersSpecMock.retrieve()).thenReturn(responseSpecMock);
		when(responseSpecMock.bodyToMono(ArgumentMatchers.<Class<Post>>notNull())).thenReturn(Mono.just(post));
		// when(postResponseMock.block()).thenReturn(post);

		Mono<Post> response = controllerMock.createPost(post);
		Assertions.assertEquals(1002, response.block().getId());
		Assertions.assertEquals("Some title", response.block().getTitle());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void test_updatePost() throws Exception {

		Post post = new Post(1003, 1003, "Some title", "Some body");

		when(webClientMock.put()).thenReturn(requestBodyUriSpecMock);
		when(requestBodyUriSpecMock.uri(anyString())).thenReturn(requestBodySpecMock);
		when(requestBodySpecMock.header(any(), any())).thenReturn(requestBodySpecMock);
		when(requestBodySpecMock.body(any())).thenReturn(requestHeadersSpecMock);
		when(requestHeadersSpecMock.retrieve()).thenReturn(responseSpecMock);
		when(responseSpecMock.bodyToMono(ArgumentMatchers.<Class<Post>>notNull())).thenReturn(Mono.just(post));

		Mono<Post> response = controllerMock.updatePost(post);
		Assertions.assertEquals(1003, response.block().getId());
		Assertions.assertEquals("Some title", response.block().getTitle());
	}	
	
	@SuppressWarnings("unchecked")
	@Test
	public void test_deletePost() throws Exception {
		
		Post post = new Post(1004, 1004, "Some title", "Some body");

		when(webClientMock.delete()).thenReturn(requestHeadersUriSpecMock);
		when(requestHeadersUriSpecMock.uri(anyString())).thenReturn(requestHeadersSpecMock);
		when(requestHeadersSpecMock.retrieve()).thenReturn(responseSpecMock);
		when(responseSpecMock.bodyToMono(ArgumentMatchers.<Class<Post>>notNull())).thenReturn(Mono.just(post));

		Mono<Post> response = controllerMock.deletePost("1");
		Assertions.assertEquals(1004, response.block().getId());
		Assertions.assertEquals("Some title", response.block().getTitle());
	}	
}
