# VpnMonitorService

## Purpose

Logs and monitors VPN/proxy access attempts for suspicious IPs.

## Features

- Increments a counter in Redis for each VPN access.
- Warns if an IP exceeds a threshold (e.g., 10 requests in 24h).
- Logs to application logs if Redis is unavailable.

## Example Usage

```java
vpnMonitorService.logVpnAccess("8.8.8.8");
```

## Core Code

```java
// VpnMonitorService.java
@Service
public class VpnMonitorService {
    private static final Logger log = LoggerFactory.getLogger(VpnMonitorService.class);
    private final RedisTemplate<String, String> redisTemplate;

    public VpnMonitorService(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void logVpnAccess(String ip) {
        try {
            String key = "vpn-monitor:" + ip;
            Long count = redisTemplate.opsForValue().increment(key);
            redisTemplate.expire(key, Duration.ofHours(24));

            if (count != null && count > 10) {
                log.warn("IP suspect — access with VPN too many requests: {}", ip);
            }
        } catch (Exception e) {
            log.error("Redis unavailable, logging only: {}", e.getMessage());
            log.info("IP suspect — VPN access: {}", ip);
        }
    }
}
```