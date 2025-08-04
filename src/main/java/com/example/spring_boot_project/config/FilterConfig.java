package com.example.spring_boot_project.config;

import com.example.spring_boot_project.Security.RateLimitingFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.web.servlet.FilterRegistrationBean;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<RateLimitingFilter> rateLimiterFilter() {
        FilterRegistrationBean<RateLimitingFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new RateLimitingFilter());
        registrationBean.addUrlPatterns("/*");
        registrationBean.setOrder(1);
        return registrationBean;
    }
}
