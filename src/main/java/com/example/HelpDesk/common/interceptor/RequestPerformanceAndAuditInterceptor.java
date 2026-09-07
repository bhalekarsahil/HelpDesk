package com.example.HelpDesk.common.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jspecify.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;


@Component
public class RequestPerformanceAndAuditInterceptor implements HandlerInterceptor {
    private static final Logger log = LoggerFactory.getLogger(RequestPerformanceAndAuditInterceptor.class);
    private static final String START_TIME_ATTR = "request_start_time";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        long startTime = System.currentTimeMillis();
        request.setAttribute(START_TIME_ATTR, startTime);

        if (handler instanceof HandlerMethod handlerMethod) {
            String controllerName = handlerMethod.getBeanType().getSimpleName();
            String methodName = handlerMethod.getMethod().getName();

            log.warn(">> Incoming Request: [{}{}] handled by {}.{}()",
                    request.getMethod(),
                    request.getRequestURI(),
                    controllerName,
                    methodName
            );
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
        Long startTime = (Long) request.getAttribute(START_TIME_ATTR);
        if (startTime != null) {
            long duration = System.currentTimeMillis() - startTime;
            int status = response.getStatus();
            if (duration > 500) {
                log.warn("<< SLOW ENDPOINT WARNING: [{} {}] returned status {} in {} ms", request.getMethod(),
                        request.getRequestURI(),
                        status,
                        duration);
            } else {
                log.info("<< Completed Request: [{} {}] returned status {} in {} ms",
                        request.getMethod(),
                        request.getRequestURI(),
                        status,
                        duration);
            }
        }
        if(ex != null){
            log.error("<< Exception bubbled past handler: {}", ex.getMessage());
        }
    }
}
