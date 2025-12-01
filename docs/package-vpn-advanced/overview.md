# VPN Advanced Package - Overview

This package provides advanced VPN/proxy detection and risk management for your Spring Boot application. It combines local heuristics, external API checks, Redis caching, and a risk scoring system to protect your endpoints from abuse by VPN/proxy users.

## Goals

- Detect VPN/proxy/hosting IPs using CIDR, ASN, and external APIs.
- Cache results for performance and resilience.
- Score and classify IPs (SAFE, CHALLENGE, MONITOR, BLOCK).
- Integrate with filters to block, challenge, or monitor suspicious traffic.
- Provide observability and easy extension.

## Architecture

- **VpnCheckerService**: Core detection logic (CIDR, ASN, API, cache).
- **VpnRiskService**: Risk scoring and classification.
- **VpnMonitorService**: Logging and monitoring of suspicious IPs.
- **AntiVpnFilter**: Integrates with Spring Security to enforce policies.
- **VpnStatus**: Enum for risk levels.
- **VpnConfig**: Policy configuration.

See the following files for detailed documentation and code.
