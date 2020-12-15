package com.obs.employeeCertificationDetails;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


@SpringBootApplication
@EnableDiscoveryClient
public class EmployeeCerificationDetailsApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmployeeCerificationDetailsApplication.class, args);
	}

}
