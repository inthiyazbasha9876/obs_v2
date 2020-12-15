package com.ojas.obs.contractcompany;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ContractComanyApplication {

	public static void main(String[] args) {
		SpringApplication.run(ContractComanyApplication.class, args);
	}

}
