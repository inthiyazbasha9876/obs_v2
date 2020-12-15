package com.ojas.obs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.ojas.obs.mailSend.MailSend;

@SpringBootApplication
@EnableScheduling
@ComponentScan("com.ojas.obs")
public class MeailSenderApplication {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(MeailSenderApplication.class, args);
		MailSend m = new MailSend();
		m.callProjectDetails();

	}

	@Bean
	@Scope(value = "singleton")
	public MailSend mainDisp() {
		return new MailSend();
	}

}
