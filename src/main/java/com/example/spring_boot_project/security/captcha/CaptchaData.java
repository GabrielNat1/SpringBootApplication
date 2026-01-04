package com.example.spring_boot_project.security.captcha;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CaptchaData {
    private String id;
    private String answerHash;
    private String ip;
    private String userAgent;
    private LocalDateTime expiresAt;
    private int attempts;

    public boolean isExpired() {
        return expiresAt.isBefore(LocalDateTime.now());
    }
}
