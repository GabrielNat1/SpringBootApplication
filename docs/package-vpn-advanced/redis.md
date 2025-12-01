# Redis & ReactiveRedis in VPN Verification

## Importance of Redis

Redis is essential for the VPN verification system to work efficiently and reliably. It provides:

- **Caching**: Stores results of VPN checks and ASN lookups, reducing external API calls and improving performance.
- **Rate Limiting & Monitoring**: Tracks suspicious IPs and their activity over time.
- **Resilience**: Allows the system to quickly respond to repeated requests from the same IP.

Without Redis, the VPN checker would:
- Perform redundant and slow external API calls for every request.
- Lose the ability to track and score IPs over time.
- Be much less effective at blocking or challenging VPN/proxy users.

## ReactiveRedisTemplate

The package uses `ReactiveRedisTemplate` for non-blocking, asynchronous Redis operations, which is ideal for high-concurrency environments.

### Example Usage in VpnCheckerService

```java
// ...existing code...
Boolean cachedResult = reactiveRedisTemplate.opsForValue()
    .get(cacheKey)
    .map(Boolean::parseBoolean)
    .block(Duration.ofMillis(200));

if (cachedResult != null)
    return cachedResult;

// After checking:
reactiveRedisTemplate.opsForValue()
    .set(cacheKey, String.valueOf(isVpn), ttl)
    .doOnError(e -> log.warn("Failed to cache VPN check for {}: {}", ip, e.getMessage()))
    .subscribe();
```
*Checks the cache before making expensive operations and stores results asynchronously.*

### Example: Caching ASN Lookups

```java
String cachedAsn = reactiveRedisTemplate.opsForValue().get(asnCacheKey).block(Duration.ofSeconds(5));
if (asn == null || asn.isEmpty()) {
    asn = fetchASN(ip).block(Duration.ofSeconds(5));
    if (asn != null && !asn.isEmpty()) {
        reactiveRedisTemplate.opsForValue().set(asnCacheKey, asn, Duration.ofHours(cacheTtlHours));
    }
}
```
*Caches ASN results to avoid repeated external lookups.*

## What Happens Without Redis?

- Every VPN check would trigger a new external API call, increasing latency and risking rate limits.
- No scoring or monitoring of IPs over time.
- The system would be less reliable and scalable.

**Conclusion:**  
Redis is a critical dependency for the advanced VPN verification package. For production use, always ensure Redis is available and properly configured.