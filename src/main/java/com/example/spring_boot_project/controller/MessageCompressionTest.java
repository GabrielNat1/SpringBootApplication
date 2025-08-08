package com.example.spring_boot_project.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/compression")
public class MessageCompressionTest {
    public String helloCompression(){
        return "test";
    }
}
