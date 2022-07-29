package ru.gb.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication(scanBasePackages = "ru.gb")
@EnableEurekaClient
public class CoreServiceApplication {
    public static void main(String[] args) {SpringApplication.run(CoreServiceApplication.class, args);}
}
