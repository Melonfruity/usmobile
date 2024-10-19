package com.usmobile.assessment.user_service.util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest; // Don't need javax since its included in jakarta
import jakarta.servlet.http.HttpServletResponse;

@Component
public class RequestLoggerInterceptor implements HandlerInterceptor {

        private static final Logger logger = LoggerFactory.getLogger(RequestLoggerInterceptor.class);

        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
            logger.info("Incoming request: method={}, uri={}, remoteAddr={}, body={}",
                    request.getMethod(),
                    request.getRequestURI(),
                    request.getRemoteAddr());
            return true;
        }
    }
