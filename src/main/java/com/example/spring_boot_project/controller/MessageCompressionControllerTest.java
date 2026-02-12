package com.example.spring_boot_project.controller;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v2")
@EnableCaching
public class MessageCompressionControllerTest {
    @GetMapping("/compress")
    public String compressMessage(String message) {
        return "compressed";
    }
}
