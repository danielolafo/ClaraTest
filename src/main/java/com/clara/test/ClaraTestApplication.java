package com.clara.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ClaraTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClaraTestApplication.class, args);
	}

}
