package ru.gb.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class WebAuthServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(WebAuthServiceApplication.class, args);
	}
}
