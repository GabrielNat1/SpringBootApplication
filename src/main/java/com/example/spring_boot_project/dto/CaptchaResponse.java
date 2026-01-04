package com.example.spring_boot_project.dto;

public record CaptchaResponse(
        String captchaId,
        String imageBase64
) {}