package com.learninjava.services;

import com.learninjava.model.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class PostService {
	
    @Autowired
	@Qualifier("PostsWebClient")
	WebClient webClient;

	public Flux<Post> getAllPost() {
		Flux<Post> response = webClient.get()
				.uri("/posts")
				.retrieve().bodyToFlux(Post.class).log();
		return response;
	}

	public Mono<Post> getPostById(String postId) {

		Mono<Post> mono = webClient. get()
				.uri("/posts/"+postId)
				.accept(MediaType. APPLICATION_JSON)
				.exchange()
				.flatMap(response -> response. bodyToMono(Post. class));

		Post  clientResponseMono = webClient.get()
				.uri("/posts/"+postId)
				.accept(MediaType. APPLICATION_JSON).retrieve().bodyToMono(Post.class).block();  // Synchronus call


		  return mono;
	}

	public Mono<Post> createPost(@RequestBody Post post) {

		return webClient.post()
				.uri("/posts")
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
				.body(BodyInserters.fromValue(post))
				.retrieve()
				.bodyToMono(Post.class);
	}

	public Mono<Post> updatePost( Post post) {
		return webClient.put()
				.uri("/posts/1")
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
				.body(BodyInserters.fromValue(post))
				.retrieve()
				.bodyToMono(Post.class);
	}


	public Mono<Post> deletePost(@PathVariable String id) {
		return webClient.delete()
				.uri("/posts/" + id)
				.retrieve()
				.bodyToMono(Post.class);
	}

}
