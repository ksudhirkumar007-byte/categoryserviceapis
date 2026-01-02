package com.expensetracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients(basePackages = "com.expensetracker.client")
@SpringBootApplication
public class ExpensetrackerApplication {

	public static void main(String[] args) {

		SpringApplication.run(ExpensetrackerApplication.class, args);
	}

}
