package ru.gb.credit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EntityScan("ru.gb")
@SpringBootApplication(scanBasePackages = "ru.gb")
@EnableEurekaClient
public class CreditServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(CreditServiceApplication.class, args);
	}
}
