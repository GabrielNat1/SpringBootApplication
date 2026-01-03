package com.example.spring_boot_project.dto;

public class MatchDTO {
    private String clientId;
    private String attendantId;

    public MatchDTO(String clientId, String attendantId) {
        this.clientId = clientId;
        this.attendantId = attendantId;
    }

    public String getClientId() {
        return clientId;
    }

    public String getAttendantId() {
        return attendantId;
    }
}
