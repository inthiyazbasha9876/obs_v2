package com.ojas.obs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class DocumentStage3Application {

	public static void main(String[] args) {
		SpringApplication.run(DocumentStage3Application.class, args);
	}

}
