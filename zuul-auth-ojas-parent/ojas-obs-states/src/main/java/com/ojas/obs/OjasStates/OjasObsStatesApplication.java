package com.ojas.obs.OjasStates;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication(scanBasePackages="com.ojas.obs.*")
@EnableDiscoveryClient
public class OjasObsStatesApplication {

	public static void main(String[] args) {
		SpringApplication.run(OjasObsStatesApplication.class, args); 
	}

}
