# VpnCheckerService

## Purpose

Detects if an IP address is associated with a VPN, proxy, or hosting provider using:

- Local CIDR and ASN heuristics
- External API lookups (e.g., ipapi.co)
- Redis caching for performance

## Key Features

- **CIDR Checks**: Matches IPs against known VPN/hosting CIDR blocks (IPv4 & IPv6).
- **ASN Checks**: Flags IPs from suspicious Autonomous System Numbers.
- **External API**: Calls external services for up-to-date VPN/proxy info.
- **Caching**: Uses Redis to cache results and ASN lookups.

## Example Usage

```java
boolean isVpn = vpnCheckerService.isVpn("8.8.8.8");
```

## Core Code

```java
// VpnCheckerService.java
@Service
public class VpnCheckerService {
    // ...existing code...

    public boolean isVpn(String ip) {
        String cacheKey = "vpn-check:" + ip;
        try {
            Boolean cachedResult = reactiveRedisTemplate.opsForValue()
            .get(cacheKey)
            .map(Boolean::parseBoolean)
            .block(Duration.ofMillis(200));
        
            if (cachedResult != null)
                return cachedResult;
        } catch (Exception e) {
            log.warn("Redis unavailable, proceeding without cache: {}", e.getMessage());
        }

        boolean isVpn = localCheck(ip);
        if (!isVpn) {
            isVpn = externalCheck(ip);
        }

        try {
            Duration ttl = Duration.ofHours(isVpn ? cacheTtlHours * 2L : cacheTtlHours);
            reactiveRedisTemplate.opsForValue()
                    .set(cacheKey, String.valueOf(isVpn), ttl)
                    .doOnError(e -> log.warn("Failed to cache VPN check for {}: {}", ip, e.getMessage()))
                    .subscribe();
        } catch (Exception e) {
            log.warn("Redis unavailable, skipping cache: {}", e.getMessage());
        }

        return isVpn;
    }

    // ...localCheck, externalCheck, isInRange, fetchASN, etc...
}
```

## CIDR and ASN Example

```java
private static final List<String> VPN_CIDR = List.of(
    "104.28.0.0/16", // Cloudflare IPv4
    "5.62.0.0/16",   // M247/OVH-ish
    "198.18.0.0/15", // testing range
    "45.13.0.0/16",
    "15.0.0.0/8",
    "2606:4700::/32" // Cloudflare IPv6
);

private static final Set<String> SUSPECT_ASN = Set.of(
    "AS16509", // Amazon
    "AS15169", // Google
    "AS9009",  // M247
    "AS14061", // DigitalOcean
    "AS13335", // Cloudflare
    "AS16276", // OVH
    "AS51167"  // Contabo
);
```

## External API Call

```java
private Mono<JsonNode> callVpnApi(String ip) {
    return webClient.get()
            .uri("/" + ip + "/json/")
            .retrieve()
            .bodyToMono(JsonNode.class)
            // ...retry and error handling...
}
```