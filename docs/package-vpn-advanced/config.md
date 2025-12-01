# VpnConfig

## Purpose

Configures the default VPN policy for the application.

## Usage

- Set the policy via the `vpn.policy` property (e.g., `MONITOR`, `BLOCK`, etc.).
- Used to determine the default action for VPN/proxy traffic.

## Code

```java
// VpnConfig.java
@Configuration
public class VpnConfig {
    @Value("${vpn.policy:MONITOR}")
    private String policy;

    public VpnStatus getPolicy() {
        return VpnStatus.valueOf(policy.toUpperCase());
    }
}
```
