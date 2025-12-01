# JWT Authentication

This project uses JWT (JSON Web Token) for stateless authentication.

## How it works

- When a user logs in, a JWT token is generated and returned.
- The token must be included in the `Authorization` header as `Bearer <token>` for protected endpoints.
- The `JwtAuthFilter` validates the token on each request.

## Environment Variable

Set your secret key in `.env`:
```
JWT_SECRET_KEY=your_secret_key
```

## Main Classes

- `JwtUtil`: Utility for generating and validating JWT tokens.
- `JwtAuthFilter`: Spring Security filter that checks the validity of JWT tokens on incoming requests.

## Example Usage

```http
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6...
```

## Code Snippets

### Generate a JWT Token

```java
// JwtUtil.java
public String generateToken(String username) {
    return Jwts.builder()
            .claims()
            .subject(username)
            .issuedAt(new Date(System.currentTimeMillis()))
            .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
            .and()
            .signWith(secretKey)
            .compact();
}
```
*Generates a JWT token for the given username, valid for 10 days.*

### Extract Username from Token

```java
// JwtUtil.java
public String extractUsername(String token) {
    try {
        if (token == null) return null;
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    } catch (ExpiredJwtException e) {
        // token expired
        return null;
    } catch (JwtException e) {
        return null;
    }
}
```
*Extracts the username from the token if valid and not expired.*

### Validate Token

```java
// JwtUtil.java
public boolean validateToken(String token) {
    try {
        Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token);
        if (token.startsWith("Bearer ")) token = token.substring(7);
        Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token);
        return true;
    } catch (JwtException e) {
        return false;
    }
}
```
*Checks if the token is valid and not tampered.*

### Filter Incoming Requests

```java
// JwtAuthFilter.java
@Override
protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    String authHeader = request.getHeader("Authorization");
    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
        filterChain.doFilter(request, response);
        return;
    }

    String token = authHeader.substring(7);
    String username = jwtUtil.extractUsername(token);
    if (username != null && SecurityContextHolder.getContext().getAuthentication() == null){
        UserDetails userDetails = userService.loadUserByUsername(username);
        if (jwtUtil.validateToken(token)){
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authToken);
        }
    }
    filterChain.doFilter(request, response);
}
```
*Checks the JWT token in the request header and authenticates the user if valid.*
