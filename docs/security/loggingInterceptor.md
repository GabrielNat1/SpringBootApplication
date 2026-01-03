# Request Logging

All incoming HTTP requests are logged for monitoring and debugging.

## How it works

- The `LoggingInterceptor` logs client IP, HTTP method, URI, date, and time.
- Logs are color-coded for better readability in the console.

## Main Class

- `LoggingInterceptor`: Intercepts and logs request details before controller execution.

## Example Log Output

```
[LOGGER] -- [IP: 127.0.0.1] [GET] [/api/v1/example] [Date: 2024-06-01] [Time: 12:34:56]
```

## Code Snippet

### Logging Interceptor

```java
// LoggingInterceptor.java
/**
     * Intercepts incoming HTTP requests before they reach the controller and logs detailed request information.
     *
     * This method captures metadata such as the client IP address, HTTP method, request URI,
     * and the exact date and time of the request. The data is formatted with ANSI color codes
     * for enhanced readability in the console logs.
     *
     * @param request  the {@link HttpServletRequest} containing the client's request information.
     * @param response the {@link HttpServletResponse} that will be sent back to the client.
     * @param handler  the chosen handler to execute, for type and/or method information.
     * @return {@code true} to allow the request to continue to the next interceptor or controller.
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
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
```

*Logs the request details including IP, method, URI, date, and time. Uses `X-Forwarded-For` if available.*
