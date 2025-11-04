package com.example.spring_boot_project.vpn.filter;

import com.example.spring_boot_project.exceptions.VpnBlockedException;
import com.example.spring_boot_project.exceptions.VpnChallengeException;
import com.example.spring_boot_project.vpn.protocol.VpnStatus;
import com.example.spring_boot_project.vpn.service.VpnMonitorService;
import com.example.spring_boot_project.vpn.service.VpnRiskService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

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

