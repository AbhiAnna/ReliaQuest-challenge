package com.challenge.api.config;

import com.challenge.api.middleware.Middleware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private Middleware middleware;

    // allows middle ware to function on all routes that start with the below pattern
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(middleware).addPathPatterns("/api/v1/**");
    }

    @Override
    public void addCorsMappings(org.springframework.web.servlet.config.annotation.CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins("http://localhost:5173") 
                .allowedMethods("GET", "POST", "OPTIONS")
                .allowedHeaders("Content-Type", "SECURITY-KEY")
                .exposedHeaders("*");
    }
}
