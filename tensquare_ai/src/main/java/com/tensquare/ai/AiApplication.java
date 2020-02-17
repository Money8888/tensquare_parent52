package com.tensquare.ai;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class AiApplication {
    public static void main(String[] args) {
        SpringApplication.run(AiApplication.class);
    }
}
