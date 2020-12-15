package com.ojas.obs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
/**
 * Title : PassportService date : 01/04/2019
 * @author <a href = "mailto : aliflaila.mohamma@ojas-it.com ">Alif</a>
 *@version 1.0
 *@see 

 */
@SpringBootApplication
@EnableDiscoveryClient
public class OjasObsOnlinePassportServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(OjasObsOnlinePassportServiceApplication.class, args);
	}

}
