# AntiVpnFilter

## Purpose

Spring Security filter that enforces VPN/proxy risk policies for incoming requests.

## How It Works

- Evaluates the client's IP using `VpnRiskService`.
- Takes action based on risk level:
  - **BLOCK**: Throws `VpnBlockedException` (request is blocked).
  - **CHALLENGE**: Throws `VpnChallengeException` (can be handled to show captcha).
  - **MONITOR**: Logs the access.
  - **SAFE**: Allows the request.

## Example Usage

Registered as a Spring bean and added to the filter chain.

## Core Code

```java
// AntiVpnFilter.java
@Component
public class AntiVpnFilter extends OncePerRequestFilter {
    private final VpnRiskService vpnRiskService;
    private final VpnMonitorService vpnMonitorService;

    public AntiVpnFilter(VpnRiskService vpnRiskService,
                         VpnMonitorService vpnMonitorService) {
        this.vpnRiskService = vpnRiskService;
        this.vpnMonitorService = vpnMonitorService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String ip = request.getRemoteAddr();
        VpnStatus status = vpnRiskService.evaluate(ip);

        switch (status) {
            case BLOCK -> throw new VpnBlockedException(ip);
            case CHALLENGE -> throw new VpnChallengeException(ip);
            case MONITOR -> vpnMonitorService.logVpnAccess(ip);
            case SAFE -> {}
        }

        filterChain.doFilter(request, response);
    }
}
```