package com.ojas.obs.regforgot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class OjasObsRegForgotApplication {

	public static void main(String[] args) {
		SpringApplication.run(OjasObsRegForgotApplication.class, args);
	}

}
