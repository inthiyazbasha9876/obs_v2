package com.ojas.obs.resourcetype;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class OjasObsResourceTypeApplication {

	public static void main(String[] args) {
		SpringApplication.run(OjasObsResourceTypeApplication.class, args);
	}

}
