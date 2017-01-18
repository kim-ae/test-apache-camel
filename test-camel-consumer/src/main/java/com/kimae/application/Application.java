package com.kimae.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.kimae.camel.config")
@ComponentScan("com.kimae.application")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
