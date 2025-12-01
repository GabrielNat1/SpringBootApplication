# VpnRiskService

## Purpose

Scores and classifies IP addresses based on VPN/proxy suspicion, using thresholds to decide actions.

## Risk Levels

- **SAFE**: No action required.
- **CHALLENGE**: Present a captcha or extra verification.
- **MONITOR**: Log and monitor activity.
- **BLOCK**: Block the request.

## Scoring Logic

- Increases score if VPN detected, decreases if not.
- Thresholds:
  - `CHALLENGE` at 6
  - `MONITOR` at 9
  - `BLOCK` at 10

## Example Usage

```java
VpnStatus status = vpnRiskService.evaluate("8.8.8.8");
```

## Core Code

```java
// VpnRiskService.java
@Service
public class VpnRiskService {
    // ...existing code...

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
```