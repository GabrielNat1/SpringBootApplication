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

    String logMessage = String.format("[LOGGER] -- [IP: %s] [%s] [%s] [Date: %s] [Time: %s]",
        clientIP, method, uri, formattedDate, formattedTime);

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
```
*Logs the request details including IP, method, URI, date, and time. Uses `X-Forwarded-For` if available.*
