package com.example.spring_boot_project.config;

import io.github.cdimascio.dotenv.Dotenv;
import io.github.cdimascio.dotenv.DotenvException;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE)
public class DotenvInitializer {
    private static Dotenv dotenv;

    @PostConstruct
    public void init() {
        try {
            dotenv = Dotenv.configure()
                    .ignoreIfMissing()
                    .load();
            
            // Seta a variável de ambiente programaticamente
            System.setProperty("ADMIN_PASSWORD", dotenv.get("ADMIN_PASSWORD"));
            System.out.println("Dotenv loaded - Admin password configured");
        } catch (DotenvException e) {
            throw new RuntimeException("Error loading .env file", e);
        }
    }

    public static String getEnvValue(String key) {
        return dotenv.get(key);
    }
}
