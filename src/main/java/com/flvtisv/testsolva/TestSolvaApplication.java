package com.flvtisv.testsolva;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import lombok.SneakyThrows;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition
public class TestSolvaApplication {
    @SneakyThrows
    public static void main(String[] args) {
        SpringApplication.run(TestSolvaApplication.class, args);
    }
}