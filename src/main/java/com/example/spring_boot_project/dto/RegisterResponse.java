package com.example.spring_boot_project.dto;

public class RegisterResponse {
    private final String publicId;
    private final String username;

    public RegisterResponse(String publicId, String username) {
        this.publicId = publicId;
        this.username = username;
    }

    public String getPublicId() {
        return publicId;
    }

    public String getUsername() {
        return username;
    }
}
