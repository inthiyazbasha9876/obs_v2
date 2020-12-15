package com.ojas.obs.billingtype;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class BillingTypeApplication {

	public static void main(String[] args) {
		SpringApplication.run(BillingTypeApplication.class, args);
	}

}
