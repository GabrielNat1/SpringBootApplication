package com.example.spring_boot_project.controller;

import com.example.spring_boot_project.Security.JwtUtil;
import com.example.spring_boot_project.model.User;
import com.example.spring_boot_project.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("auth")
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody Map<String, String> request){
        User user = UserService.registerUser(request.get("username"), "password");
        return ResponseEntity.ok(user);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody Map<String, String> request){
        Optional<User> userOptional = userService.findByUsername(request.get("username"));
        if (userOptional.isPresent() && userOptional.get().getPassword().equals(request.get("password"))){
            String token = JwtUtil.generateToken(userOptional.get().getUsername());
            return ResponseEntity.ok(Map.of("token", token));
        }
        return ResponseEntity.status(401).body("Invalid username or password");
    }
}
