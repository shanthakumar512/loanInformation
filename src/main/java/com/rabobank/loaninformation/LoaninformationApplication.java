package com.rabobank.loaninformation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class LoaninformationApplication {

	public static void main(String[] args) {
		SpringApplication.run(LoaninformationApplication.class, args);
	}

}
