package com.example.spring_boot_project.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

/*
    model chatroom
 */
@Entity
public class ChatRoom {
    @Id
    private String id;

    private String clientId;
    private String attendantId;
    private boolean active;
}
