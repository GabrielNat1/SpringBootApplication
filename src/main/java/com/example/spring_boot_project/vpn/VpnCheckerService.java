package com.example.spring_boot_project.vpn;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.Duration;
import java.util.*;
import java.util.regex.Pattern;

@Service
public class VpnCheckerService {

    private final RedisTemplate<String, String> redisTemplate;
    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${vpn.api.url:https://ipapi.co}")
    private String vpnApiUrl;

    @Value("${vpn.cache.ttl.hours:6}")
    private int cacheTtlHours;

    private static final Set<String> KNOWN_VPN_RANGES = new HashSet<>();
    private static final List<Pattern> SUSPICIOUS_ASN_PATTERNS = new ArrayList<>();

    static {
        KNOWN_VPN_RANGES.add("104.28."); // Cloudflare
        KNOWN_VPN_RANGES.add("172.16."); // Private ranges (proxy cases)
        KNOWN_VPN_RANGES.add("198.18."); // Testing range
        KNOWN_VPN_RANGES.add("45.13.");
        KNOWN_VPN_RANGES.add("5.62.");   // VPN Gate e OVH
        KNOWN_VPN_RANGES.add("15.");     // AWS Global Accelerator

        // ASN patterns
        SUSPICIOUS_ASN_PATTERNS.add(Pattern.compile("(?i).*amazon.*"));
        SUSPICIOUS_ASN_PATTERNS.add(Pattern.compile("(?i).*google.*"));
        SUSPICIOUS_ASN_PATTERNS.add(Pattern.compile("(?i).*cloudflare.*"));
        SUSPICIOUS_ASN_PATTERNS.add(Pattern.compile("(?i).*digitalocean.*"));
        SUSPICIOUS_ASN_PATTERNS.add(Pattern.compile("(?i).*ovh.*"));
        SUSPICIOUS_ASN_PATTERNS.add(Pattern.compile("(?i).*m247.*"));
        SUSPICIOUS_ASN_PATTERNS.add(Pattern.compile("(?i).*contabo.*"));
    }

    public VpnCheckerService(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public boolean isVpn(String ip) {
        String cacheKey = "vpn-check:" + ip;
        try {
            String cached = redisTemplate.opsForValue().get(cacheKey);
            if (cached != null) return Boolean.parseBoolean(cached);
        } catch (Exception e) {
            System.err.println("Redis unavailable, continuing without cache: " + e.getMessage());
        }

        boolean isVpn = localCheck(ip);
        if (!isVpn) {
            isVpn = externalCheck(ip);
        }

        try {
            Duration ttl = Duration.ofHours(isVpn ? cacheTtlHours * 2L : cacheTtlHours);
            redisTemplate.opsForValue().set(cacheKey, String.valueOf(isVpn));
            redisTemplate.expire(cacheKey, ttl);
        } catch (Exception e) {
            System.err.println("Redis unavailable, skipping cache: " + e.getMessage());
        }

        return isVpn;
    }

    private boolean localCheck(String ip) {
        try {
            InetAddress inet = InetAddress.getByName(ip);
            if (inet.isSiteLocalAddress() || inet.isLoopbackAddress()) return false;

            for (String range : KNOWN_VPN_RANGES) {
                if (ip.startsWith(range)) return true;
            }

            String asnKey = "asn:" + ip;
            String asnName = redisTemplate.opsForValue().get(asnKey);

            if (asnName == null) {
                // Optional
                String hostname = inet.getCanonicalHostName();
                asnName = hostname != null ? hostname.toLowerCase() : "";
                redisTemplate.opsForValue().set(asnKey, asnName);
                redisTemplate.expire(asnKey, Duration.ofDays(1));
            }

            for (Pattern pattern : SUSPICIOUS_ASN_PATTERNS) {
                if (pattern.matcher(asnName).find()) {
                    return true;
                }
            }

        } catch (UnknownHostException e) {
            System.err.println("Resolving IP " + ip + ": " + e.getMessage());
        }

        return false;
    }

    private boolean externalCheck(String ip) {
        try {
            String url = String.format("%s/%s/json/", vpnApiUrl, ip);
            Map<?, ?> response = restTemplate.getForObject(url, Map.class);

            if (response == null) return false;

            Object proxy = response.get("proxy");
            Object hosting = response.get("hosting");
            Object vpn = response.get("vpn");

            return (toBool(proxy) || toBool(hosting) || toBool(vpn));

        } catch (Exception e) {
            System.err.println("Error api: " + e.getMessage());
        }

        return false;
    }

    private boolean toBool(Object value) {
        if (value == null) return false;
        String s = value.toString().toLowerCase();
        return s.equals("true") || s.equals("1") || s.equals("yes");
    }
}
