package com.development.taskmgmt_pro.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException)
            throws IOException, ServletException {
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setContentType("application/json");

        Map<String, Object> error = new HashMap<>();
        error.put("status", HttpStatus.FORBIDDEN.value());
        error.put("error", HttpStatus.FORBIDDEN.getReasonPhrase());
        error.put("message", "Access denied: Insufficient permissions to perform this action");

        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getOutputStream(),error);
    }
}
