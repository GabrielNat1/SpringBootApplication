package com.example.spring_boot_project.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class MessageCompressionTest {
    @GetMapping("/compression")
    public String helloCompression(){
        return "test";
    }
}
