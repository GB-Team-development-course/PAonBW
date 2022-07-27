package ru.gbank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@EntityScan("ru.gbank")
@SpringBootApplication (scanBasePackages = "ru.gbank")
public class DebitServiceApplication {
    public static void main(String[] args) { SpringApplication.run(DebitServiceApplication.class, args); }
}
