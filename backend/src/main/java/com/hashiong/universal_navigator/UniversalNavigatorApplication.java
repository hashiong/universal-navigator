package com.hashiong.universal_navigator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class UniversalNavigatorApplication {

	public static void main(String[] args) {
		SpringApplication.run(UniversalNavigatorApplication.class, args);
	}

}