package com.ojas.obs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication(scanBasePackages="com.ojas.obs.*")
@EnableDiscoveryClient
public class OjasObsEducationtypeApplication {

	public static void main(String[] args) {
		SpringApplication.run(OjasObsEducationtypeApplication.class, args);
	}

}
