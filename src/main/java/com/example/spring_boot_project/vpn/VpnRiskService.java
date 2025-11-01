package com.example.spring_boot_project.vpn;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class VpnRiskService {

    private final RedisTemplate<String, String> redisTemplate;
    private final VpnCheckerService vpnCheckerService;

    // Thresholds
    private final int safeThreshold = 3;
    private final int challengeThreshold = 6;
    private final int monitorThreshold = 9;
    private final int blockThreshold = 10;

    public VpnRiskService(RedisTemplate<String, String> redisTemplate,
                          VpnCheckerService vpnCheckerService) {
        this.redisTemplate = redisTemplate;
        this.vpnCheckerService = vpnCheckerService;
    }

    public VpnStatus evaluate(String ip) {
        String key = "vpn-risk:" + ip;
        int score = 0;

        try {
            String cached = redisTemplate.opsForValue().get(key);
            if (cached != null) score = Integer.parseInt(cached);
        } catch (Exception e) {
            System.err.println("Redis unavailable, using default score: " + e.getMessage());
        }

        if (vpnCheckerService.isVpn(ip)) {
            score += 3;
        } else {
            score = Math.max(score - 1, 0);
        }

        VpnStatus status;
        if (score >= blockThreshold) status = VpnStatus.BLOCK;
        else if (score >= monitorThreshold) status = VpnStatus.MONITOR;
        else if (score >= challengeThreshold) status = VpnStatus.CHALLENGE;
        else status = VpnStatus.SAFE;

        try {
            redisTemplate.opsForValue().set(key, String.valueOf(score), Duration.ofHours(24));
        } catch (Exception e) {
            System.err.println("Redis unavailable, skipping score persistence: " + e.getMessage());
        }

        return status;
    }
}
