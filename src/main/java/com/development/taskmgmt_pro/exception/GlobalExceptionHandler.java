package com.development.taskmgmt_pro.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;


@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(DuplicateUserException.class)
    public ResponseEntity<Map<String, Object>> handleDuplicateUserException(DuplicateUserException ex, HttpServletRequest request) {
        logger.warn("Duplicate User at {} : {}", request.getRequestURI(), ex.getMessage());
        return new ResponseEntity<>(errorResponse(HttpStatus.CONFLICT, ex.getMessage(), null, request.getRequestURI()), HttpStatus.CONFLICT);
    }

    private Map<String, Object> errorResponse(HttpStatus status, String message, Map<String, String> errors, String path) {
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", status.value());
        response.put("error", status.getReasonPhrase());
        response.put("message", message);
        response.put("path", path);
        if (errors != null && !errors.isEmpty()) {
            response.put("errors", errors);
        }
        return response;
    }
}
