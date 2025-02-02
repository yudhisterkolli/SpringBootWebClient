package com.learninjava.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class ApiService {

    @Autowired
    @Qualifier("EmployeeClient")
    private WebClient webClient;

    public Mono<String> getAllData() {
        return webClient.get()
                .uri("https://example.com/data")
                .retrieve()
                .bodyToMono(String.class)
                .doOnSuccess(response -> {
                    // Process successful response
                    System.out.println("Received response: " + response);
                })
                .doOnError(error -> {
                    // Handle error
                    System.err.println("Error fetching data: " + error.getMessage());
                });
    }
  
    // Update
  
    // Delete
    public Mono<Void> deleteById(String id) {
        return webClient.delete()
                .uri("https://example.com/data/{id}", id)
                .retrieve()
                .bodyToMono(Void.class);
    }
}