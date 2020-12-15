package com.ojas.obs.c2hstatus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class C2hStatusApplication {

	public static void main(String[] args) {
		SpringApplication.run(C2hStatusApplication.class, args);
	}

}
