package com.ojas.obs.emp_edu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class EmployeeEducationDetailsApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmployeeEducationDetailsApplication.class, args);
	}

}
