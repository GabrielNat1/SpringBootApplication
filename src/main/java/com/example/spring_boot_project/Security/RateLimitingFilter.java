package com.example.spring_boot_project.Security;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class RateLimitingFilter implements Filter {

    private final Map<String, Bucket> buckets = new ConcurrentHashMap<>();

    private Bucket createNewBucket() {
        // limit to 10 requests per minute
        Bandwidth limit = Bandwidth.builder()
                .capacity(10)
                .refillIntervally(10, Duration.ofMinutes(1))
                .build();
        return Bucket.builder()
                .addLimit(limit)
                .build();
    }

    /**
     * Applies a rate limiting filter to incoming HTTP requests based on the client's IP address.
     *
     * This method intercepts each request, extracts the client IP, and associates it with a
     * token bucket that tracks how many requests that IP can make within a defined time window.
     * If the IP still has available tokens, the request is allowed to proceed through the filter chain.
     * Otherwise, the filter responds with HTTP status {@code 429 Too Many Requests}.
     *
     * @param request  the {@link ServletRequest} containing the client's request data.
     * @param response the {@link ServletResponse} used to send the response back to the client.
     * @param chain    the {@link FilterChain} used to pass the request to the next filter in the chain.
     *
     * @throws IOException if an I/O error occurs while processing the request.
     * @throws ServletException if an error occurs during request filtering.
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        String ip = req.getRemoteAddr();

        Bucket bucket = buckets.computeIfAbsent(ip, k -> createNewBucket());

        if (bucket.tryConsume(1)) {
            chain.doFilter(request, response);
        } else {
            HttpServletResponse res = (HttpServletResponse) response;
            res.setStatus(429);
            res.getWriter().write("Too Many Requests - Rate limit exceeded");
        }
    }
}