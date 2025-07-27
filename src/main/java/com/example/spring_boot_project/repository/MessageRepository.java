package com.example.spring_boot_project.repository;

import org.springframework.stereotype.Repository;

@Repository
public class MessageRepository {
    public String getMessage(){
        return "Hello from MessageRepository!";
    }
}
