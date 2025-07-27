package com.example.spring_boot_project;

import config.DotenvInitializer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootProjectApplication {
	public static void main(String[] args) {
		DotenvInitializer.init();
		SpringApplication.run(SpringBootProjectApplication.class, args);
	}
}
