package com.ojas.obs.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 
 * @author tshiva
 *
 */
@SpringBootApplication(scanBasePackages = "com.ojas.obs.*")
@EnableDiscoveryClient
public class OjasObsKyeApplication {

	public static void main(String[] args) {
		SpringApplication.run(OjasObsKyeApplication.class, args);
	}
}