package com.example.spring_boot_project.vpn.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.databind.JsonNode;

import reactor.core.publisher.Mono;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.Duration;
import java.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class VpnCheckerService {

    private static final Logger log = LoggerFactory.getLogger(VpnCheckerService.class);

    private final RedisTemplate<String, String> redisTemplate;
    private final WebClient webClient;

    private final String vpnApiUrl;

    @Value("${vpn.cache.ttl.hours:6}")
    private int cacheTtlHours;

    private static final List<String> VPN_CIDR = List.of(
        "104.28.0.0/16", 
        "5.62.0.0/16", 
        "198.18.0.0/15",
        "45.13.0.0/16", 
        "15.0.0.0/8" 
    );

    public VpnCheckerService(RedisTemplate<String, String> redisTemplate,
                                @Value("${vpn.api.url:https://ipapi.co}") String apiUrl) {
        this.redisTemplate = redisTemplate;
        this.vpnApiUrl = apiUrl;
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

            for (String cidr : VPN_CIDR) {
                if (isInRange(ip, cidr)) return true;
            }

            // Check ASN
            String asn = redisTemplate.opsForValue().get("asn:" + ip);
            if (asn == null) {
                asn = fetchASN(ip).block();
                if (asn != null && !asn.isEmpty()) {
                    redisTemplate.opsForValue().set("asn:" + ip, asn, Duration.ofHours(cacheTtlHours));
                }
            }
        } catch (UnknownHostException e) {
            log.error("Resolving IP {}: {}", ip, e.getMessage());
        }

        return false;
    }

    private Mono<String> fetchASN(String ip) {
        return webClient.get()
                .uri("/" + ip + "/json/")
                .retrieve()
                .bodyToMono(JsonNode.class)
                .map(json -> json.path("asn").asText("")) // "AS15169"
                .timeout(Duration.ofSeconds(3))
                .onErrorReturn("");
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

            return (toBool(response.get("proxy")) || toBool(response.get("hosting")) || toBool(response.get("vpn")));

        } catch (Exception e) {
            log.error("Error api: {}", e.getMessage());
            return false;
        }
    }

    private boolean isInRange(String ip, String cidr) {
        try {
            String[] parts = cidr.split("/");
            InetAddress target = InetAddress.getByName(ip);
            InetAddress base = InetAddress.getByName(parts[0]);
            int mask = Integer.parseInt(parts[1]);

            byte[] targetBytes = target.getAddress();
            byte[] baseBytes = base.getAddress();

            int byteMask = 0xFFFFFFFF << (32 - mask);

            int targetInt = toInt(targetBytes);
            int baseInt = toInt(baseBytes);

            return (targetInt & byteMask) == (baseInt & byteMask);

        } catch (Exception e) {
            return false;
        }
    }

    private int toInt(byte[] bytes) {
        int result = 0;
        for (byte b : bytes) {
            result = (result << 8) | (b & 0xFF);
        }
        return result;
    }

    private boolean toBool(Object value) {
        if (value == null) return false;
        String s = value.toString().toLowerCase();
        return s.equals("true") || s.equals("1") || s.equals("yes");
    }
}
