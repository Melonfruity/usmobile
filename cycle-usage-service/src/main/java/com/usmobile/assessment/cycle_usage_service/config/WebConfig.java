package com.usmobile.assessment.cycle_usage_service.config;

import com.usmobile.assessment.cycle_usage_service.util.RequestLoggerInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final RequestLoggerInterceptor requestLoggerInterceptor;

    @Autowired
    public WebConfig(RequestLoggerInterceptor requestLoggerInterceptor) {
        this.requestLoggerInterceptor = requestLoggerInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(requestLoggerInterceptor);
    }
}
