package com.example.spring_boot_project.vpn.service;

import com.fasterxml.jackson.databind.JsonNode;

import inet.ipaddr.IPAddress;
import inet.ipaddr.IPAddressString;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ExchangeFilterFunctions;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;
import reactor.util.retry.Retry;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.Duration;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
            "104.28.0.0/16",    // Cloudflare IPv4
            "5.62.0.0/16",      // M247/OVH-ish
            "198.18.0.0/15",    // testing range
            "45.13.0.0/16",
            "15.0.0.0/8",
            "2606:4700::/32"    // Cloudflare IPv6 (exemplo)
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

    private static final Pattern ASN_EXTRACTOR = Pattern.compile("(AS\\d+)", Pattern.CASE_INSENSITIVE);

    public VpnCheckerService(RedisTemplate<String, String> redisTemplate,
                             @Value("${vpn.api.url:https://ipapi.co}") String apiUrl) {

        this.redisTemplate = redisTemplate;
        this.vpnApiUrl = apiUrl;

        HttpClient httpClient = HttpClient.create()
                .responseTimeout(Duration.ofSeconds(4));

        this.webClient = WebClient.builder()
                .baseUrl(apiUrl)
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .filter(ExchangeFilterFunctions.statusError(
                        status -> status.isError(),
                        res -> new RuntimeException("API error: " + res.statusCode())
                ))
                .build();
    }

    public boolean isVpn(String ip) {
        String cacheKey = "vpn-check:" + ip;
        try {
            String cached = redisTemplate.opsForValue().get(cacheKey);
            if (cached != null) return Boolean.parseBoolean(cached);
        } catch (Exception e) {
            log.warn("Redis unavailable, proceeding without cache: {}", e.getMessage());
        }

        boolean isVpn = localCheck(ip);

        if (!isVpn) {
            isVpn = externalCheck(ip);
        }

        try {
            Duration ttl = Duration.ofHours(isVpn ? cacheTtlHours * 2L : cacheTtlHours);
            redisTemplate.opsForValue().set(cacheKey, String.valueOf(isVpn), ttl);
        } catch (Exception e) {
            log.warn("Redis unavailable, skipping cache: {}", e.getMessage());
        }

        return isVpn;
    }

    /**
     * Performs a local VPN check for the given IP.
     * 
     * Steps:
     * 1. Skips private and loopback addresses.
     * 2. Checks against known VPN/hosting CIDR blocks (IPv4 & IPv6).
     * 3. Looks up ASN in Redis cache or fetches from external API.
     *    Flags IPs from suspicious ASNs.
     * 
     * @param ip the IP address to check
     * @return true if the IP is likely a VPN, false otherwise
     */
    private boolean localCheck(String ip) {
        try {
            InetAddress inet = InetAddress.getByName(ip);
            if (inet.isSiteLocalAddress() || inet.isLoopbackAddress()) return false;

            // Check CIDR list (supports IPv4 and IPv6)
            for (String cidr : VPN_CIDR) {
                if (isInRange(ip, cidr)) {
                    log.debug("IP {} matched CIDR {}", ip, cidr);
                    return true;
                }
            }

            // ASN: try cache first, then fetch once and cache (synchronous block)
            String asnCacheKey = "asn:" + ip;
            try {
                String cachedAsn = redisTemplate.opsForValue().get(asnCacheKey);
                String asn = cachedAsn;
                if (asn == null || asn.isEmpty()) {
                    // return example -> "AS15169"
                    asn = fetchASN(ip).block(); 
                    if (asn != null && !asn.isEmpty()) {
                        try {
                            redisTemplate.opsForValue().set(asnCacheKey, asn, Duration.ofHours(cacheTtlHours));
                        } catch (Exception e) {
                            log.debug("Failed to cache ASN: {}", e.getMessage());
                        }
                    }
                }
                if (asn != null && !asn.isEmpty() && isSuspiciousASN(asn)) {
                    log.debug("IP {} flagged by ASN {}", ip, asn);
                    return true;
                }
            } catch (Exception e) {
                log.debug("ASN lookup/cache error for {}: {}", ip, e.getMessage());
            }

        } catch (UnknownHostException e) {
            log.debug("Resolving IP {} failed: {}", ip, e.getMessage());
        }
        return false;
    }

    // External API call wrapper
    private Mono<JsonNode> callVpnApi(String ip) {
        return webClient.get()
                .uri("/" + ip + "/json/")
                .retrieve()
                .bodyToMono(JsonNode.class)
                .retryWhen(Retry.backoff(2, Duration.ofMillis(300))
                        .filter(ex -> {
                            // retry for network or server errors but not for 4xx client errors (except maybe 429)
                            if (ex instanceof WebClientResponseException) {
                                int code = ((WebClientResponseException) ex).getStatusCode().value();
                                return code >= 500 || code == 429; // retry 5xx and 429
                            }
                            return true;
                        }))
                .timeout(Duration.ofSeconds(3), Mono.empty())
                .onErrorResume(e -> {
                    log.debug("callVpnApi error for {}: {}", ip, e.getMessage());
                    return Mono.empty();
                });
    }

    /**
     * Fetches the ASN for a given IP from the external API.
     * 
     * - Tries "asn" field first, then "org" if missing.
     * - Extracts AS number (e.g., "AS15169"), or prefixes numeric values with "AS".
     * - Returns empty string on failure or missing data.
     * 
     * @param ip the IP address to query
     * @return a Mono emitting the ASN string, or empty if unavailable
     */
    private Mono<String> fetchASN(String ip) {
        return callVpnApi(ip)
                .map(json -> {
                    if (json == null) return "";
                    // ipapi sometimes returns "asn" or "org" - try both
                    String asnField = "";
                    if (json.has("asn") && !json.path("asn").isMissingNode()) {
                        asnField = json.path("asn").asText("");
                    } else if (json.has("org")) {
                        asnField = json.path("org").asText("");
                    }

                    Matcher m = ASN_EXTRACTOR.matcher(asnField);
                    if (m.find()) {
                        return m.group(1).toUpperCase();
                    }
                  
                    if (asnField.matches("\\d+")) {
                        return "AS" + asnField;
                    }
                    return "";
                })
                .onErrorReturn("");
    }

    private boolean externalCheck(String ip) {
        try {
            JsonNode json = callVpnApi(ip).block();
            if (json == null) return false;

            return toBool(json.get("vpn"))
                    || toBool(json.get("proxy"))
                    || toBool(json.get("hosting"))
                    || toBool(json.get("datacenter"))
                    || toBool(json.get("tor"));
        } catch (Exception e) {
            log.debug("externalCheck failed for {}: {}", ip, e.getMessage());
            return false;
        }
    }

    // CIDR check supporting IPv4 and IPv6
    private boolean isInRange(String ip, String cidr) {
        try {
            IPAddressString ipAddrStr = new IPAddressString(ip);
            IPAddress ipAddr = ipAddrStr.toAddress();

            IPAddressString subnetStr = new IPAddressString(cidr);
            IPAddress subnet = subnetStr.toAddress();

            return subnet.contains(ipAddr);
        } catch (Exception e) {
            log.warn("Error verifying {} in range {}: {}", ip, cidr, e.getMessage());
            return false;
        }
    }

    private boolean isSuspiciousASN(String asn) {
        if (asn == null || asn.isEmpty()) return false;
        return SUSPECT_ASN.contains(asn.toUpperCase());
    }

    private boolean toBool(JsonNode node) {
        if (node == null || node.isNull()) return false;
        String s = node.asText("").trim().toLowerCase();
        return s.equals("true") || s.equals("1") || s.equals("yes") || s.equals("y");
    }

}
