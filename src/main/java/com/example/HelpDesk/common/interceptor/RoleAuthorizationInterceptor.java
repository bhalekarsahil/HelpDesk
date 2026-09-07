package com.example.HelpDesk.common.interceptor;

import com.example.HelpDesk.common.security.RequireRole;
import com.example.HelpDesk.exception.UnauthorizedException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jspecify.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Arrays;
import java.util.List;

@Component
public class RoleAuthorizationInterceptor implements HandlerInterceptor {
    private static final Logger log = LoggerFactory.getLogger(RoleAuthorizationInterceptor.class);
    private static final String ROLE_HEADER = "X-User-Role";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod handlerMethod)) {
            return true;
        }
        RequireRole requireRole = handlerMethod.getMethodAnnotation(RequireRole.class);
        if (requireRole == null)
            requireRole = handlerMethod.getBeanType().getAnnotation(RequireRole.class);
        if (requireRole == null) return true;
        // Extract the role passed by the client
        String userRole = request.getHeader(ROLE_HEADER);
        if (userRole == null || userRole.trim().isEmpty()) {
            log.warn("Unauthorized access attempt to [{}{}]: mising ' {} ' header",
                    request.getMethod(), request.getRequestURI(), ROLE_HEADER
            );
            throw new UnauthorizedException("Missing required header: " + ROLE_HEADER);
        }

        // Check if the user's role is in the allowed listCheck if the
        List<String> allowedRoles = Arrays.asList(requireRole.role());
        boolean hasPermission = allowedRoles.stream()
                .anyMatch(role -> role.equalsIgnoreCase(userRole.trim()));

        if (!hasPermission) {
            log.warn("Access denied for role '{}' on [{}{}]. Required: {}",
                    ROLE_HEADER, request.getMethod(), request.getRequestURI(), allowedRoles
            );
            throw new UnauthorizedException("'Access Denied: Role'" + userRole + "'does not have permission for this action'");
        }
        log.info("Access granted to [{} {}] for role '{}'",
                request.getMethod(), request.getRequestURI(), userRole);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
