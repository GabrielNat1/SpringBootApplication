package com.example.spring_boot_project.Security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;

public class LoggingInterceptor implements HandlerInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(LoggingInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String clientIp = request.getRemoteAddr();
        String method = request.getMethod();
        String timestamp = java.time.LocalDateTime.now().toString();

        logger.info("IP: {}, Method: {}, Route: {}, Date: {}",
                clientIp,
                method,
                request.getRequestURI(),
                timestamp);

        return true;
    }
}
