package com.learninjava.controller;

import com.learninjava.model.EmployeeResponse;
import com.learninjava.model.Post;
import com.learninjava.services.EmployeeService;
import com.learninjava.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/microservice/webclient")
public class PostController {
	@Autowired
	PostService postService;
    @GetMapping("/v1/posts")
	public ResponseEntity<Flux<Post>> getAllPost(){
    	Flux<Post> emps= postService.getAllPost();
    	return ResponseEntity.status(HttpStatus.OK).body(emps);
	}


	@GetMapping("/v1/post/{id}")
	public ResponseEntity<Mono<Post>> getPost(@PathVariable String id) {

		Mono<Post> postMono = postService.getPostById(id);

		return new ResponseEntity(postMono, HttpStatus.OK);
	}

	@PostMapping(path="/v1/post", consumes= MediaType.APPLICATION_JSON_VALUE,
			produces=MediaType.APPLICATION_JSON_VALUE)
	public Mono<Post> createPost(@RequestBody Post post) {

		return postService.createPost(post);
	}

	@PutMapping(path="/v1/post", consumes=MediaType.APPLICATION_JSON_VALUE)
	public Mono<Post> updatePost(@RequestBody Post post) {

	    return postService.updatePost(post);
	}

	@DeleteMapping(path="/v1/post/{id}")
	public Mono<Post> deletePost(@PathVariable String id) {

		return postService.deletePost(id);
	}

}
