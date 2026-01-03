package com.example.spring_boot_project.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

/*
    model message
 */
@Entity
public class Message {
    @Id
    @GeneratedValue
    private long id;

    private String roomId;
    private String sender;
    private String content;
    private LocalDateTime createAt;
}
