package com.example.spring_boot_project.controller;

import com.example.spring_boot_project.exceptions.InvalidCredentialsException;
import com.example.spring_boot_project.exceptions.UsernameAlreadyExistsException;
import com.example.spring_boot_project.security.JwtUtil;
import com.example.spring_boot_project.dto.LoginResponse;
import com.example.spring_boot_project.dto.RegisterRequest;
import com.example.spring_boot_project.dto.RegisterResponse;
import com.example.spring_boot_project.model.User;
import com.example.spring_boot_project.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v2/auth")
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
    public ResponseEntity<RegisterResponse> registerUser(
            @RequestBody RegisterRequest request,
            HttpServletRequest httpRequest) {

        String ipAddress = httpRequest.getRemoteAddr();
        String userAgent = httpRequest.getHeader("User-Agent");


        User savedUser = userService.registerUser(
                request.getUsername(),
                request.getPassword(),
                request.getEmail(),
                request.getTelephone(),
                ipAddress,
                userAgent
        );


        return ResponseEntity.status(201).body(
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
        String username = request.get("username");
        String rawPassword = request.get("password");

        Optional<User> userOptional = userService.findByUsername(username);
//        if (userOptional.isPresent()) {
//            System.out.println("RAW: " + rawPassword);
//            System.out.println("ENCODED FROM DB: " + userOptional.get().getPassword());
//        }

        if (userOptional.isPresent() &&
                passwordEncoder.matches(rawPassword, userOptional.get().getPassword())) {

            User user = userOptional.get();
            String token = jwtUtil.generateToken(user.getUsername());

            return ResponseEntity.ok(
                    new LoginResponse(
                            user.getPublicId(),
                            user.getUsername(),
                            user.getEmail(),
                            user.getTelephone(),
                            token,
                            user.getCreatedIp(),
                            user.getCreatedDevice(),
                            user.getCreatedAt().toString()
                    )
            );
        }

        throw new InvalidCredentialsException();
    }

    @GetMapping("/login")
    public ResponseEntity<?> login(Authentication authentication) {
        return ResponseEntity.ok().body("Logged in as: " + authentication.getName());
    }
}
