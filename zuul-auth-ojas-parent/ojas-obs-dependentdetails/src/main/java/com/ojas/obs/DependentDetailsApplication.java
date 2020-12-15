package com.ojas.obs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
/**
 * 
 * @author srinukummari
 *
 *
 *
 *
 *
 *
 */
@SpringBootApplication//(scanBasePackages="com.ojas.obs.*")
@EnableDiscoveryClient
public class DependentDetailsApplication {

	public static void main(String[] args) {
		SpringApplication.run(DependentDetailsApplication.class, args);
	}

}


