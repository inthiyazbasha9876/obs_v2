package com.ojas.obs.main;

import org.apache.log4j.BasicConfigurator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication(scanBasePackages = "com.ojas.obs.*")
@EnableDiscoveryClient
public class RoleManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(RoleManagementApplication.class, args);
		BasicConfigurator.configure();
	}

}
