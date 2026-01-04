package com.example.spring_boot_project.dto;

public record CaptchaValidateRequest(
        String captchaId,
        String answer
) {}
