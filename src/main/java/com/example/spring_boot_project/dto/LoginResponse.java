package com.example.spring_boot_project.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginResponse {
    private String publicId;
    private String username;
    private String email;
    private String telephone;
    private String token;
    private String createdIp;
    private String createdDevice;
    private String createdAt;
}
