package com.ojas.obs.projecttechstack;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ProjectTechStackApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjectTechStackApplication.class, args);
	}

}
