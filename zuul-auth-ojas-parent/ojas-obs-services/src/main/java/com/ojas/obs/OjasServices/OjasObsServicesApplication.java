package com.ojas.obs.OjasServices;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication(scanBasePackages="com.ojas.obs.*")
@EnableDiscoveryClient
public class OjasObsServicesApplication {

	public static void main(String[] args) {
		SpringApplication.run(OjasObsServicesApplication.class, args);
	}

}
