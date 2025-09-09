package com.projects.spring.restapidemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class RestApIdemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(RestApIdemoApplication.class, args);
    }

}
