package com.example.spring_boot_project.controller;

import cn.apiclub.captcha.Captcha;
import com.example.spring_boot_project.security.CaptchaUtilOld;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("api/v1/test-captcha")
public class CaptchaTestControllerOld {

    @GetMapping
    public Map<String, String> getCaptcha(HttpSession session) {
        Captcha captcha = CaptchaUtilOld.createCaptcha(200, 50);
        //System.out.println("--------------" + captcha.getImage());
        String encoded = CaptchaUtilOld.encodeCaptcha(captcha);

        session.setAttribute("captcha", captcha.getAnswer());

        return Map.of("image", "data:image/png;base64," + encoded);
    }

    @PostMapping
    public ResponseEntity<String> validateCaptcha(@RequestBody Map<String, String> request, HttpSession session) {
        String userInput = request.get("captcha");
        String captchaAnswer = (String) session.getAttribute("captcha");

        if (captchaAnswer == null) {
            return ResponseEntity.badRequest().body("Captcha not found or expired.");
        }

        session.removeAttribute("captcha");

        if (captchaAnswer.equalsIgnoreCase(userInput)) {
            return ResponseEntity.ok("Captcha is correct!");
        } else {
            return ResponseEntity.status(400).body("Captcha is incorrect!");
        }
    }
}
