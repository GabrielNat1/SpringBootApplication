# Integration Guide

## 1. Register Beans

Ensure all VPN-related services and filters are registered as Spring beans.

## 2. Add Filter to Security Chain

Add `AntiVpnFilter` to your security filter chain before authentication filters.

## 3. Configure Properties

Set the following properties in your `application.properties`:

```
vpn.api.url=https://ipapi.co
vpn.cache.ttl.hours=6
vpn.policy=MONITOR
```

## 4. Redis Setup

Ensure Redis is running and accessible for caching and monitoring.

## 5. Exception Handling

Handle `VpnBlockedException` and `VpnChallengeException` in your global exception handler to return appropriate responses.

## 6. Example SecurityConfig Snippet

```java
// ...existing code...
.addFilterBefore(antiVpnFilter, UsernamePasswordAuthenticationFilter.class)
.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
// ...existing code...
```