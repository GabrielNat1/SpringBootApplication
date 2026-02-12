package com.example.spring_boot_project.controller;

import com.example.spring_boot_project.dto.CaptchaValidateRequest;
import com.example.spring_boot_project.security.captcha.CaptchaService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v2/captcha")
public class CaptchaController {
    private final CaptchaService captchaService;

    public CaptchaController(CaptchaService captchaService) {
        this.captchaService = captchaService;
    }

    @GetMapping
    public Object generate(HttpServletRequest request) {
        return captchaService.generate(
                request.getRemoteAddr(),
                request.getHeader("User-Agent")
        );
    }

    @PostMapping("/validate")
    public boolean validate(
            @RequestBody CaptchaValidateRequest req,
            HttpServletRequest request
    ) {
        return captchaService.validate(
                req.captchaId(),
                req.answer(),
                request.getRemoteAddr(),
                request.getHeader("User-Agent")
        );
    }
}
