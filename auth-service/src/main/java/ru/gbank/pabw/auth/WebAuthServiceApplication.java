package ru.gbank.pabw.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class WebAuthServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(WebAuthServiceApplication.class, args);
	}
}
