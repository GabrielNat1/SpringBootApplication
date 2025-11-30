package com.example.spring_boot_project.vpn.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.Duration;
import java.util.*;
import java.util.regex.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class VpnCheckerService {

    private static final Logger log = LoggerFactory.getLogger(VpnCheckerService.class);

    private final RedisTemplate<String, String> redisTemplate;
    private final WebClient webClient;

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

    public VpnCheckerService(RedisTemplate<String, String> redisTemplate,
                                @Value("${vpn.api.url:https://ipapi.co}") String apiUrl) {
        this.redisTemplate = redisTemplate;
        this.webClient = WebClient.builder()
                        .baseUrl(apiUrl)
                        .build();
    }

    public boolean isVpn(String ip) {
        String cacheKey = "vpn-check:" + ip;
        try {
            String cached = redisTemplate.opsForValue().get(cacheKey);
            if (cached != null) return Boolean.parseBoolean(cached);
        } catch (Exception e) {
            log.error("Redis unavailable, proceeding without cache: " + e.getMessage());
        }

        boolean isVpn = localCheck(ip);
        if (!isVpn) {
            isVpn = externalCheck(ip);
        }

        try {
            Duration ttl = Duration.ofHours(isVpn ? cacheTtlHours * 2L : cacheTtlHours);
            redisTemplate.opsForValue()
                .set(cacheKey, String.valueOf(isVpn), ttl);
        } catch (Exception e) {
            log.error("Redis unavailable, skipping cache: {}", e.getMessage());
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
            log.error("Resolving IP {}: {}", ip, e.getMessage());
        }

        return false;
    }

    private boolean externalCheck(String ip) {
        try {
            Map<?, ?> response = webClient.get()
                    .uri("/" + ip + "/json/")
                    .retrieve()
                    .bodyToMono(Map.class)
                    .timeout(Duration.ofSeconds(3))
                    .onErrorResume(e -> {
                        log.warn("API timeout/erro: {}", e.getMessage());
                        return Mono.empty();
                    })
                    .block();

            if (response == null) return false;

            return (toBool("proxy") || toBool("hosting") || toBool("vpn"));

        } catch (Exception e) {
            log.error("Error api: {}", e.getMessage());
            return false;
        }
    }

    private boolean toBool(Object value) {
        if (value == null) return false;
        String s = value.toString().toLowerCase();
        return s.equals("true") || s.equals("1") || s.equals("yes");
    }
}
