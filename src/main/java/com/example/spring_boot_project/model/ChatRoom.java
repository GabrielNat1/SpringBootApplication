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

    public void setId(String id) {
        this.id = id;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public void setAttendantId(String attendantId) {
        this.attendantId = attendantId;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getId() {
        return id;
    }

    public String getClientId() {
        return clientId;
    }

    public String getAttendantId() {
        return attendantId;
    }

    public boolean isActive() {
        return active;
    }
}
