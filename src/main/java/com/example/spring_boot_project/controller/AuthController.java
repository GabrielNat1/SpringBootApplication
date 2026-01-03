package com.example.spring_boot_project.controller;

import com.example.spring_boot_project.Security.JwtUtil;
import com.example.spring_boot_project.dto.RegisterRequest;
import com.example.spring_boot_project.dto.RegisterResponse;
import com.example.spring_boot_project.model.User;
import com.example.spring_boot_project.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final UserService userService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthController(UserService userService, BCryptPasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    /**
     * Registers a new user in the system.
     *
     * This endpoint receives user registration data via a JSON request body,
     * checks whether the username already exists, and if not, creates a new user
     * with an encoded password and a default role ("USER").
     *
     * Additionally, metadata such as creation date, client IP, and device information
     * may be recorded (depending on the service implementation).
     *
     * @param request the {@link RegisterRequest} object containing the username and password.
     * @return a {@link ResponseEntity} containing a success message if the user is registered,
     *         or an error message if the username already exists.
     */
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequest request) {
        Optional<User> existingUser = userService.findByUsername(request.getUsername());

        if (existingUser.isPresent()) {
            return ResponseEntity.badRequest().body("Username already exists.");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole("USER");
        User savedUser = userService.save(user);

        return ResponseEntity.ok(
                new RegisterResponse(
                        savedUser.getPublicId(),
                        savedUser.getUsername()
                )
        );
    }

    /**
     * Authenticates a user and generates a JWT token for access authorization.
     *
     * This endpoint validates the provided credentials (username and password)
     * against the stored user data. If authentication succeeds, a JWT token is
     * generated and returned to the client.
     *
     * The token can be used in subsequent requests as a Bearer token in the
     * Authorization header to access protected routes.
     *
     * @param request a {@link Map} containing the keys "username" and "password".
     * @return a {@link ResponseEntity} containing the generated JWT token if the
     *         credentials are valid, or an error response if authentication fails.
     */
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

            String token = jwtUtil.generateToken(userOptional.get().getUsername());

            return ResponseEntity.ok(Map.of("token", token));
        }

        return ResponseEntity.status(401).body("Invalid username or password.");
    }

    @GetMapping("/login")
    public ResponseEntity<?> login(Authentication authentication) {
        return ResponseEntity.ok().body("Logged in as: " + authentication.getName());
    }
}
