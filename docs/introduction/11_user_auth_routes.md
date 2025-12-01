# 👤 User Authentication Routes - Spring Boot API

## 📌 API Endpoints Overview

### 🔐 Authentication Flow
POST /api/auth/register → POST /api/auth/login → GET /api/auth/me

## 🛠 Route Definitions

### 1. User Registration
```json
POST /api/auth/register
Content-Type: application/json

{
"username": "newuser",
"email": "user@example.com",
"password": "SecurePass123!",
"roles": ["USER"]
}

Responses:
- 201 Created: User registered successfully
- 400 Bad Request: Validation errors
- 409 Conflict: Username/email already exists
```

### 2. User Login
```json
POST /api/auth/login
Content-Type: application/json

{
"username": "existinguser",
"password": "SecurePass123!"
}

Response (200 OK):
{
"accessToken": "eyJhbGci...",
"refreshToken": "eyJhbGci...",
"expiresIn": 3600,
"tokenType": "Bearer"
}
```

### 3. Get Current User
```json
GET /api/auth/me
Authorization: Bearer <access_token>

Response (200 OK):
{
"id": 123,
"username": "existinguser",
"email": "user@example.com",
"roles": ["USER"]
}
```

### 4. Refresh Token
```java
POST /api/auth/refresh
Content-Type: application/json

{
"refreshToken": "eyJhbGci..."
}

Response (200 OK):
{
"accessToken": "new.generated.token",
"expiresIn": 3600
}
```

### 5. Password Reset
```java
POST /api/auth/password-reset-request
Content-Type: application/json

{
"email": "user@example.com"
}

POST /api/auth/password-reset
Content-Type: application/json

{
"token": "reset-token",
"newPassword": "NewSecurePass123!"
}
```

## 🏗 Controller Implementation
```java
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @PostMapping("/register")
    public ResponseEntity<UserResponse> registerUser(
        @Valid @RequestBody RegisterRequest request) {
        // Registration logic
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> authenticateUser(
        @Valid @RequestBody LoginRequest request) {
        // Authentication logic
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponse> getCurrentUser(
        @CurrentUser UserPrincipal userPrincipal) {
        // Return current user
    }
}
```

## 📝 Request/Response Models

### Register Request
```java
public class RegisterRequest {
@NotBlank
@Size(min=3, max=20)
private String username;

    @NotBlank
    @Email
    private String email;
    
    @NotBlank
    @Size(min=8, max=40)
    private String password;
    
    private Set<String> roles;
    // Getters and setters
}
```

### Auth Response
```java
public class AuthResponse {
private String accessToken;
private String refreshToken;
private Long expiresIn;
private String tokenType = "Bearer";
// Getters and setters
}
```

## 🔒 Security Considerations

1. **Password Handling**:
    - Never store plain text passwords
    - Use BCrypt password hashing

2. **Rate Limiting**:
    - Implement on login/registration endpoints

3. **Input Validation**:
    - Validate all request payloads
    - Sanitize user inputs

4. **Session Management**:
    - Invalidate old tokens on password change
    - Implement token blacklist for logout

## 🎯 Best Practices

1. **Endpoint Design**:
    - Use consistent naming (/auth prefix)
    - Follow REST conventions

2. **Response Standardization**:
    - Uniform success/error formats
    - Include relevant metadata (timestamps, etc.)

3. **Documentation**:
    - Swagger/OpenAPI documentation
    - Clear error code definitions

4. **Testing**:
    - Unit tests for all auth scenarios
    - Integration tests for full flow

## 📚 Additional Features

1. **Email Verification**:
    - `/api/auth/verify-email?token=`

2. **Two-Factor Auth**:
    - `/api/auth/2fa/verify`

3. **Social Logins**:
    - `/api/auth/oauth2/{provider}`

4. **Account Locking**:
    - After N failed attempts