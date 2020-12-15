package com.ojas.obs.sez;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class SezMasterApplication {

	public static void main(String[] args) {
		SpringApplication.run(SezMasterApplication.class, args);
	}

}
