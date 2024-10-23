package com.usmobile.assessment.cycle_usage_service.util;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class RequestLoggerInterceptor implements HandlerInterceptor {

        private static final Logger logger = LoggerFactory.getLogger(RequestLoggerInterceptor.class);

        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
            logger.debug("Incoming request: method={}, uri={}, remoteAddr={}, body={}",
                    request.getMethod(),
                    request.getRequestURI(),
                    request.getRemoteAddr());
            return true;
        }
    }
