package com.ojas.security.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.ComponentScan;

/**
 * Spring boot app.
 *
 * 
 */
@SpringBootApplication
@EnableZuulProxy
//@EnableEurekaServer 
public class Application {

    public static void main(String[] args) {

        SpringApplication.run(Application.class, args);
    }
}
