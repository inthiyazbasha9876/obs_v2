package com.ojas.obs.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(basePackages = "com.ojas.*")
public class TitleApplication {

	public static void main(String[] args) {
		SpringApplication.run(TitleApplication.class, args);
	}

}
