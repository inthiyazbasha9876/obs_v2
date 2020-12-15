package com.ojas.obs.employmentdetails;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication(scanBasePackages="com.ojas.obs.employmentdetails.*")
@EnableDiscoveryClient
public class OjasObsEmploymentDetailsApplication {

	public static void main(String[] args) {
		SpringApplication.run(OjasObsEmploymentDetailsApplication.class, args);
	}

}
