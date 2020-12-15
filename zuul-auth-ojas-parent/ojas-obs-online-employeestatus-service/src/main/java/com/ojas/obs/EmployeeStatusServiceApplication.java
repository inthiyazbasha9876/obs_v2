package com.ojas.obs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
/**
 * 
 * @author Manohar
 *
 */
@SpringBootApplication
@EnableDiscoveryClient
public class EmployeeStatusServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmployeeStatusServiceApplication.class, args);
	}

}
