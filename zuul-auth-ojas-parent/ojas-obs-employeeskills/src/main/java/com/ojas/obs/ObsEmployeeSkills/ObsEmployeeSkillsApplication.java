package com.ojas.obs.ObsEmployeeSkills;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication(scanBasePackages= "com.ojas.obs.*")
@EnableDiscoveryClient
public class ObsEmployeeSkillsApplication {

	public static void main(String[] args) {
		SpringApplication.run(ObsEmployeeSkillsApplication.class, args);
	}

}