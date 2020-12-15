package com.ojas.obs.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
/**
 * 
 * @author uyashwanth
 *
 */
@SpringBootApplication
@ComponentScan(basePackages = "com.ojas.obs.*")
public class EmployeeBuApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmployeeBuApplication.class, args);
	}

}
