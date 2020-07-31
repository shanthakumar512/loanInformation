package com.rabobank.loaninformation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.PropertySource;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication()
@EnableDiscoveryClient
@EnableSwagger2
@PropertySource("classpath:application.properties")
public class LoaninformationApplication {

	public static void main(String[] args) {
		SpringApplication.run(LoaninformationApplication.class, args);
	}

}
