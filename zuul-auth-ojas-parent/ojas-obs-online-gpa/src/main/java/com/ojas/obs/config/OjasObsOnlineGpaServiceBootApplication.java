package com.ojas.obs.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages="com.ojas.obs.*")
public class OjasObsOnlineGpaServiceBootApplication {

	public static void main(String[] args) {
		SpringApplication.run(OjasObsOnlineGpaServiceBootApplication.class, args);
	}

}


