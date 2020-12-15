package com.ojas.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 
 * @author nsrikanth
 *
 */


@SpringBootApplication(scanBasePackages="com.ojas.*")
@EnableDiscoveryClient
public class OjasObsDesignationApplication {

	public static void main(String[] args) {
		SpringApplication.run(OjasObsDesignationApplication.class, args);
	}

}
