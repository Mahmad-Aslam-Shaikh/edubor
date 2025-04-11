package com.enotes_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditAware")
public class EduborApplication {

	public static void main(String[] args) {
		SpringApplication.run(EduborApplication.class, args);
	}

}
