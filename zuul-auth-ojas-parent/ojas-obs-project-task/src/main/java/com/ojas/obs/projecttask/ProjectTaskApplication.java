package com.ojas.obs.projecttask;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ProjectTaskApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjectTaskApplication.class, args);
	}

}
