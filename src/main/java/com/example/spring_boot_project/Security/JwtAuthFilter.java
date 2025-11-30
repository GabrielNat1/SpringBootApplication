package com.example.spring_boot_project.Security;

import com.example.spring_boot_project.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    private final UserService userService;

    public JwtAuthFilter(JwtUtil jwtUtil, UserService userService) {
        this.jwtUtil = jwtUtil;
        this.userService = userService;
    }

    /**
     * Filters incoming HTTP requests to validate JWT authentication tokens.
     *
     * This method intercepts each HTTP request, extracts the "Authorization" header,
     * and checks whether it contains a valid JWT token. If a valid token is found,
     * it authenticates the user and sets the authentication context in Spring Security.
     *
     * If the header is missing or does not start with "Bearer ", the request is passed
     * along the filter chain without authentication. This allows public endpoints
     * to continue functioning normally.
     *
     * @param request the incoming {@link HttpServletRequest} containing the client's request data.
     * @param response the {@link HttpServletResponse} used to send the response back to the client.
     * @param filterChain the {@link FilterChain} used to pass the request to the next filter in the chain.
     *
     * @throws ServletException if an error occurs during request filtering.
     * @throws IOException if an I/O error occurs while processing the request.
     */
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
}
