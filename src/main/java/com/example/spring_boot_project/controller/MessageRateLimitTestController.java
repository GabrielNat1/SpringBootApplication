package com.example.spring_boot_project.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class MessageRateLimitTestController {
    @GetMapping("/testController")
    public String helloLimitTest(){
        return "hello rate limit";
    }
}
