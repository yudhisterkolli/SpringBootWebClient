package com.learninjava;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.reactive.config.EnableWebFlux;


@SpringBootApplication
@EnableWebFlux
public class SpringBootWebClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootWebClientApplication.class, args);
	}

}
