# VpnStatus Enum

## Purpose

Represents the risk classification for an IP address.

## Values

- `SAFE`: No VPN/proxy detected, allow access.
- `CHALLENGE`: Suspicious, require extra verification (e.g., captcha).
- `MONITOR`: Monitor and log activity.
- `BLOCK`: Block the request.

## Code

```java
// VpnStatus.java
public enum VpnStatus {
    SAFE,
    CHALLENGE,
    MONITOR,
    BLOCK
}
```
