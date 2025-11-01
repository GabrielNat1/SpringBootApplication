package com.example.spring_boot_project.exceptions;

public class VpnChallengeException extends RuntimeException {
    public VpnChallengeException(String ip) {
        super("Challenge required for VPN IP: " + ip);
    }
}
