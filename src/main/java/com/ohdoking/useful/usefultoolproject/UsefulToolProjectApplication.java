package com.ohdoking.useful.usefultoolproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class UsefulToolProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(UsefulToolProjectApplication.class, args);
	}
}
