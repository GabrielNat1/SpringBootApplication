package com.example.spring_boot_project.controller;

import com.example.spring_boot_project.Security.JwtUtil;
import com.example.spring_boot_project.model.User;
import com.example.spring_boot_project.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("auth")
public class AuthController {
    private final UserService userService;
    private final BCryptPasswordEncoder passwordEncoder;

    public AuthController(UserService userService, BCryptPasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody Map<String, String> request) {
        if (!request.containsKey("username") || !request.containsKey("password")) {
            return ResponseEntity.badRequest().body("Username and password are required.");
        }

        String username = request.get("username");
        String rawPassword = request.get("password");

        if (userService.findByUsername(username).isPresent()) {
            return ResponseEntity.status(409).body("Username already exists.");
        }

        String encodedPassword = passwordEncoder.encode(rawPassword);
        User user = userService.registerUser(username, encodedPassword);

        return ResponseEntity.ok(user);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody Map<String, String> request) {
        if (!request.containsKey("username") || !request.containsKey("password")) {
            return ResponseEntity.badRequest().body("Username and password are required.");
        }

        String username = request.get("username");
        String rawPassword = request.get("password");

        Optional<User> userOptional = userService.findByUsername(username);
        if (userOptional.isPresent() &&
                passwordEncoder.matches(rawPassword, userOptional.get().getPassword())) {

            String token = JwtUtil.generateToken(userOptional.get().getUsername());
            return ResponseEntity.ok(Map.of("token", token));
        }

        return ResponseEntity.status(401).body("Invalid username or password.");
    }
}
