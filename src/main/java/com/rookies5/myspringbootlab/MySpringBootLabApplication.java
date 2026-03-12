package com.rookies5.myspringbootlab;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MySpringBootLabApplication {

	public static void main(String[] args) {
        SpringApplication.run(MySpringBootLabApplication.class, args);
	}

    @Bean
    public String hello() {
        System.out.println("=====Spring Bean 입니다. ====");
        return "Hello Bean";
    }
}
