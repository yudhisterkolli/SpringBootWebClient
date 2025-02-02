package com.learninjava.services;

import com.learninjava.model.Employee;
import com.learninjava.model.EmployeeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import reactor.core.publisher.Mono;

@Service
public class EmployeeService {
	
    @Autowired
	@Qualifier("EmployeeClient")    
	WebClient webClient;
	
	public Mono<EmployeeResponse> getEmployees() {
		
		Mono<EmployeeResponse> response = webClient.get()
				//.uri("/employees")
		 .retrieve().bodyToMono(EmployeeResponse.class);

		return response;
	}
	 public Mono<Employee> fetchEmployeeById(int id) {
		    return webClient.get()
		      //.uri("/employees/{id}", id)
		      .exchangeToMono(this::handleResponse);
		  }
	 
	 public Mono<ResponseEntity<Employee>> createEmployee(Employee newEmployee) {

		    return webClient.post()
		      .uri("/employees")
		      .body(Mono.just(newEmployee), Employee.class)
		      .retrieve()
		      .onStatus(HttpStatus::is4xxClientError, response -> {
		        //logError("Client error occurred");
		        return Mono.error(new WebClientResponseException
		          (response.statusCode().value(), "Bad Request", null, null, null));
		      })
		      .onStatus(HttpStatus::is5xxServerError, response -> {
		        //logError("Server error occurred");
		        return Mono.error(new WebClientResponseException
		          (response.statusCode().value(), "Server Error", null, null, null));
		      })
		      .toEntity(Employee.class);
		  }
	
	 private Mono<Employee> handleResponse(ClientResponse response) {

		    if (response.statusCode().is2xxSuccessful()) {
		      return response.bodyToMono(Employee.class);
		    } 
		    else if (response.statusCode().is4xxClientError()) {
		      // Handle client errors (e.g., 404 Not Found)
		      //return Mono.error(new EmployeeNotFoundException("Employee not found"));
		    	   return Mono.error(new RuntimeException("Unexpected error"));
		    } 
		    else if (response.statusCode().is5xxServerError()) {
		      // Handle server errors (e.g., 500 Internal Server Error)
		      return Mono.error(new RuntimeException("Server error"));
		    } 
		    else {
		      // Handle other status codes as needed
		      return Mono.error(new RuntimeException("Unexpected error"));
		    }
		  }

}
