package com.example.HelpDesk.config;

import com.example.HelpDesk.common.interceptor.RequestPerformanceAndAuditInterceptor;
import com.example.HelpDesk.common.interceptor.RoleAuthorizationInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    private final RequestPerformanceAndAuditInterceptor auditInterceptor;
    private final RoleAuthorizationInterceptor roleAuthorizationInterceptor;
    public WebMvcConfig(RequestPerformanceAndAuditInterceptor auditInterceptor, RoleAuthorizationInterceptor roleAuthorizationInterceptor) {
        this.auditInterceptor = auditInterceptor;
        this.roleAuthorizationInterceptor = roleAuthorizationInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(auditInterceptor)
                .addPathPatterns("/api/v1/**")
                .excludePathPatterns("/error", "/swagger-ui/**", "/v3/api-docs/**");

        registry.addInterceptor(roleAuthorizationInterceptor)
                .addPathPatterns("/api/v1/**")
                .excludePathPatterns("/error");

    }
}
