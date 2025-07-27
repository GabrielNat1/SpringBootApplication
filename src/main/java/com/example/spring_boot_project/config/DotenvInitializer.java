package com.example.spring_boot_project.config;

import io.github.cdimascio.dotenv.Dotenv;
import io.github.cdimascio.dotenv.DotenvException;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DotenvInitializer {
    public static Dotenv dotenv;

   @PostConstruct
    public static void init(){
       try {
           dotenv = Dotenv.configure()
                   .ignoreIfMissing()
                   .load();

           System.out.println("Dotenv loaded");
       } catch (DotenvException e) {
           throw new RuntimeException(e);
       }

   }
}
