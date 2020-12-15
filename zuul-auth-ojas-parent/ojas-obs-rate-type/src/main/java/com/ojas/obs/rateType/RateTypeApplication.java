package com.ojas.obs.rateType;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class RateTypeApplication {

	public static void main(String[] args) {
		SpringApplication.run(RateTypeApplication.class, args);
	}

}
