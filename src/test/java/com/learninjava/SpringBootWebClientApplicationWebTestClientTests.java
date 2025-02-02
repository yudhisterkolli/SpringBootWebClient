package com.learninjava;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import com.learninjava.model.Post;


/** Integration Testing - Approach 3 - Using WebTestClient
 *  Requires the actual service to be up and running - This is why have to use
 *  the exact same requests and responses while testing
 * 
 * @author learninjava.com
 *
 */
@SpringBootTest()
@ExtendWith(SpringExtension.class)
class SpringBootWebClientApplicationWebTestClientTests {

	@Autowired
	private static WebTestClient webTestClient;

	@BeforeAll
  public static void setup() {
		webTestClient = WebTestClient.bindToServer()
            .baseUrl("http://localhost:8080/microservice/webclient/v1")
            .build();
  }

	@Test
	public void test_getPost() throws Exception {
		
		Post post = new Post(1, 1, 
				"sunt aut facere repellat provident occaecati excepturi optio reprehenderit", 
				"quia et suscipit\nsuscipit recusandae consequuntur expedita et cum\nreprehenderit molestiae ut ut quas totam\nnostrum rerum est autem sunt rem eveniet architecto");
		
		webTestClient.get().uri("/post/{id}", 1)
			.accept(MediaType.APPLICATION_JSON)
			.exchange()
			.expectStatus().isOk()
			.expectBody(Post.class).isEqualTo(post);
	}
	
	@Test
	public void test_createPost() throws Exception {
		
		Post post = new Post(1, 101, 
				"sunt aut facere repellat provident occaecati excepturi optio reprehenderit", 
				"quia et suscipit\nsuscipit recusandae consequuntur expedita et cum\nreprehenderit molestiae ut ut quas totam\nnostrum rerum est autem sunt rem eveniet architecto");
		
		webTestClient.post().uri("/post")
			.accept(MediaType.APPLICATION_JSON)
			.body(BodyInserters.fromValue(post))
			.exchange()
			.expectStatus().isOk()
			.expectBody(Post.class).isEqualTo(post);
	}
	
	@Test
	public void test_updatePost() throws Exception {
		
		Post post = new Post(1003, 1, "Some title", "Some body");

		webTestClient.put().uri("/post")
			.accept(MediaType.APPLICATION_JSON)
			.body(BodyInserters.fromValue(post))
			.exchange()
			.expectStatus().isOk()
			.expectBody(Post.class).isEqualTo(post);
	}
	
	@Test
	public void test_deletePost() throws Exception {
		
		Post post = new Post(0, 0, null, null); 
		
		webTestClient.delete().uri("/post/{id}", 1)
			.accept(MediaType.APPLICATION_JSON)
			.exchange()
			.expectStatus().isOk()
			.expectBody(Post.class).isEqualTo(post);
	}	
}
