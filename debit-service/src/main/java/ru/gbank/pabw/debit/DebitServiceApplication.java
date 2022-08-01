package ru.gbank.pabw.debit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EntityScan("ru.gbank")
@SpringBootApplication (scanBasePackages = "ru.gbank")
@EnableEurekaClient
public class DebitServiceApplication {
    public static void main(String[] args) { SpringApplication.run(DebitServiceApplication.class, args); }
}
