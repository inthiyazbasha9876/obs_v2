package com.ojas.obs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class SarStatusApplication {

	public static void main(String[] args) {
		SpringApplication.run(SarStatusApplication.class, args);
	}

}
