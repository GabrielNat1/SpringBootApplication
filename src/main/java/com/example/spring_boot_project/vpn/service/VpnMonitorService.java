package com.example.spring_boot_project.vpn.service;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class VpnMonitorService {

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
                System.out.println("IP suspect — access with VPN too many requests: " + ip);
            }
        } catch (Exception e) {
            System.err.println("Redis unavailable, logging only: " + e.getMessage());
            System.out.println("IP suspect — VPN access: " + ip);
        }
    }
}