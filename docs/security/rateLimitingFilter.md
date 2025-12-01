# Rate Limiting

Rate limiting helps protect the API from abuse by restricting the number of requests per IP.

## How it works

- The `RateLimitingFilter` uses Bucket4j to allow a fixed number of requests per minute per IP address.
- If the limit is exceeded, the API responds with HTTP 429 (Too Many Requests).

## Main Class

- `RateLimitingFilter`: Servlet filter that applies rate limiting.

## Configuration

- Default: 10 requests per minute per IP.

## Example Response

```
HTTP/1.1 429 Too Many Requests
Too Many Requests - Rate limit exceeded
```

## Code Snippet

### Rate Limiting Filter

```java
// RateLimitingFilter.java
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
```
*Limits each IP to 10 requests per minute. If exceeded, returns HTTP 429.*
