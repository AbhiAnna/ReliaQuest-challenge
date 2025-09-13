package com.challenge.api.middleware;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class Middleware implements HandlerInterceptor {

    // Middleware to authenticate before APIs are accessed
    // In real application this would be dynamically generate token on log in with an expiration time
    private static final String KEY = "key";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        String securityKey = request.getHeader("SECURITY-KEY");
                
        // for front end integration
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            return true;
        }

        if (securityKey != null && securityKey.equals(KEY)) {
            return true;
        } else {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid Security Key");
            return false;
        }
    }
}
