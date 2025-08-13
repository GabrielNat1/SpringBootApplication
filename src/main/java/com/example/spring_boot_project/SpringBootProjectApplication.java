package com.example.spring_boot_project;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootProjectApplication {
    public static void main(String[] args) {
        Dotenv dotenv = Dotenv.configure()
                .ignoreIfMissing()
                .load();

        setSystemPropertyIfPresent(dotenv, "ADMIN_USERNAME");
        setSystemPropertyIfPresent(dotenv, "ADMIN_PASSWORD");
        setSystemPropertyIfPresent(dotenv, "JWT_SECRET_KEY");
        setSystemPropertyIfPresent(dotenv, "SERVER_COMPRESSION_MIN_RESPONSE_SIZE");

        SpringApplication.run(SpringBootProjectApplication.class, args);
    }

    private static void setSystemPropertyIfPresent(Dotenv dotenv, String key) {
        String value = dotenv.get(key);
        if (value != null) {
            System.setProperty(key, value);
        }
    }
}
