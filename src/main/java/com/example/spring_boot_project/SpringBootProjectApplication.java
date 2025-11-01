package com.example.spring_boot_project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

import com.example.spring_boot_project.config.DotenvInitializer;

@SpringBootApplication
@EnableCaching
public class SpringBootProjectApplication {
    public static void main(String[] args) {
    	SpringApplication app = new SpringApplication(SpringBootProjectApplication.class);
        app.addInitializers(new DotenvInitializer());
        app.run(args);
    }
}
