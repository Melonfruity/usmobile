package com.usmobile.assessment.cycle_usage_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling  // Enable scheduling
public class CycleUsageServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CycleUsageServiceApplication.class, args);
	}

}
