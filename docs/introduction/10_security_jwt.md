# 🔒 JWT Authentication in Spring Boot

## 📌 JWT Overview
JSON Web Tokens (JWT) are an open standard for securely transmitting information between parties as a JSON object.

## 🏗 JWT Structure
header.payload.signature

Header: { "alg": "HS256", "typ": "JWT" }
Payload: { "sub": "123", "name": "John", "iat": 1516239022 }
Signature: HMACSHA256(base64UrlEncode(header) + "." + base64UrlEncode(payload), secret)

text

## 🛠 Spring Boot Implementation

### 1. Dependencies
```java
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-api</artifactId>
    <version>0.11.5</version>
</dependency>
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-impl</artifactId>
    <version>0.11.5</version>
    <scope>runtime</scope>
</dependency>
```

### 2. Security Configuration
```java
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
            .authorizeRequests()
            .antMatchers("/api/auth/**").permitAll()
            .anyRequest().authenticated()
            .and()
            .addFilter(new JwtAuthenticationFilter(authenticationManager()))
            .addFilter(new JwtAuthorizationFilter(authenticationManager()))
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }
}
```

### 3. JWT Utility Class
```java
public class JwtTokenUtil {

    private final String SECRET_KEY = "your-secret-key";
    private final long EXPIRATION_TIME = 864_000_000; // 10 days
    
    public String generateToken(UserDetails userDetails) {
        return Jwts.builder()
            .setSubject(userDetails.getUsername())
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
            .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
            .compact();
    }
    
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
```

## 🔐 Authentication Flow

1. Client sends credentials to `/login`
2. Server validates credentials
3. Server generates JWT and returns it
4. Client includes JWT in `Authorization: Bearer <token>` header
5. Server validates JWT on each request

## 🛡️ Security Best Practices

1. **Token Storage**:
    - Use HttpOnly cookies for web apps
    - Secure storage for mobile apps

2. **Token Expiration**:
    - Short-lived access tokens (15-30 mins)
    - Long-lived refresh tokens (7-30 days)

3. **Secret Management**:
    - Never hardcode secrets
    - Use environment variables or secret management tools

4. **Additional Security**:
    - Implement IP whitelisting
    - Add device fingerprinting
    - Use token blacklisting for logout

## 🚨 Common Vulnerabilities

1. **None Algorithm Vulnerability**:
    - Always validate the algorithm in your JWT parser

2. **Secret Bruteforcing**:
    - Use strong secrets (minimum 32 characters)

3. **Token Sidejacking**:
    - Implement CSRF protection
    - Use strict CORS policies

4. **Information Exposure**:
    - Don't store sensitive data in JWTs

## 🔄 Refresh Token Implementation
```java
public class RefreshToken {
private String token;
private Date expiryDate;
// Getters and setters
}

@Service
public class AuthService {

    public AuthResponse refreshToken(String refreshToken) {
        // Validate refresh token
        // Generate new access token
        // Optionally generate new refresh token
    }
}
```

## 📝 JWT Claims Best Practices

1. Standard Claims:
    - `sub` (subject): User ID
    - `iat` (issued at): Token creation time
    - `exp` (expiration): Token expiration time

2. Custom Claims:
    - `roles`: User roles/permissions
    - `tenant`: For multi-tenant apps
    - `iss`: Token issuer

## 📚 Additional Resources

1. [JWT Official Website](https://jwt.io)
2. [Spring Security Documentation](https://spring.io/projects/spring-security)
3. [OWASP JWT Cheat Sheet](https://cheatsheetseries.owasp.org/cheatsheets/JSON_Web_Token_Cheat_Sheet.html)