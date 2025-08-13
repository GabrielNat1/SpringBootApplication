package com.example.spring_boot_project.Security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.lang.NonNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LoggingInterceptor implements HandlerInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(LoggingInterceptor.class);
    
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_BLUE = "\u001B[34m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_CYAN = "\u001B[36m";
    private static final String ANSI_WHITE = "\u001B[37m";
    @Override
    public boolean preHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) {
        String clientIP = getClientIp(request);
        String method = request.getMethod();
        String uri = request.getRequestURI();
        LocalDateTime now = LocalDateTime.now();
        
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        
        String formattedDate = now.format(dateFormatter);
        String formattedTime = now.format(timeFormatter);

        String logMessage = String.format("%s[LOGGER]%s -- %s[IP: %s]%s %s[%s]%s %s[%s]%s %s[Date: %s]%s %s[Time: %s]%s",
            ANSI_CYAN, ANSI_RESET,
            ANSI_GREEN, clientIP, ANSI_RESET,
            ANSI_BLUE, method, ANSI_RESET,
            ANSI_YELLOW, uri, ANSI_RESET,
            ANSI_WHITE, formattedDate, ANSI_RESET,
            ANSI_WHITE, formattedTime, ANSI_RESET);

        logger.info(logMessage);
        return true;
    }

    private String getClientIp(HttpServletRequest request) {
        String xfHeader = request.getHeader("X-Forwarded-For");
        if (xfHeader == null || xfHeader.isEmpty()) {
            String ip = request.getRemoteAddr();
            return ip.equals("0:0:0:0:0:0:0:1") ? "127.0.0.1" : ip;
        }
        return xfHeader.split(",")[0];
    }
}
