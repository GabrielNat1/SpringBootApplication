package com.example.spring_boot_project.vpn;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class VpnConfig {

    @Value("${vpn.policy:MONITOR}")
    private String policy;

    public VpnStatus getPolicy() {
        return VpnStatus.valueOf(policy.toUpperCase());
    }
}