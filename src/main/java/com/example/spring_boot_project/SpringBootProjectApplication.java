package com.example.spring_boot_project;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootProjectApplication {
    public static void main(String[] args) {
        // Carrega o .env antes de iniciar o Spring
        Dotenv dotenv = Dotenv.configure()
                .ignoreIfMissing()
                .load();
        
        // Seta a variável de ambiente programaticamente
        System.setProperty("ADMIN_PASSWORD", dotenv.get("ADMIN_PASSWORD"));
        
        SpringApplication.run(SpringBootProjectApplication.class, args);
    }
}
