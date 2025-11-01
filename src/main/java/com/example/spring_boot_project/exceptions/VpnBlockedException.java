package com.example.spring_boot_project.exceptions;

public class VpnBlockedException extends RuntimeException {
    public VpnBlockedException(String ip) {
        super("Access blocked for VPN IP: " + ip);
    }
}
