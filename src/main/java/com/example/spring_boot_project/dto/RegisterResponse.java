package com.example.spring_boot_project.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RegisterResponse {
    private final String publicId;
    private final String username;
}
