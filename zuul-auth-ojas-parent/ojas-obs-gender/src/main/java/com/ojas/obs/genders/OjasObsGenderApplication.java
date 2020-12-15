package com.ojas.obs.genders;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication(scanBasePackages= {"com.ojas.obs.*"})
@EnableDiscoveryClient
public class OjasObsGenderApplication {

	public static void main(String[] args) {
		SpringApplication.run(OjasObsGenderApplication.class, args);
	}

}
