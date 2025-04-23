package com.development.taskmgmt_pro.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, HttpServletRequest request) {
        logger.warn("Validation failed for {} : {}", request.getRequestURI(), ex.getMessage());
        List<Map<String, String>> errors = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> Map.of(
                        "field", error.getField(),
                        "message", error.getDefaultMessage(),
                        "value", String.valueOf(error.getRejectedValue())))
                .collect(Collectors.toList());
        return new ResponseEntity<>(errorResponse(HttpStatus.BAD_REQUEST, "Validation failed", errors, request.getRequestURI()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Map<String, Object>> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex, HttpServletRequest request) {
        logger.warn("Invalid parameter type for {} : {}", request.getRequestURI(), ex.getMessage());
        String message = String.format("Invalid value '%s' for parameter '%s'. Expected type: %s",
                ex.getValue(), ex.getName(), ex.getRequiredType().getSimpleName());
        return new ResponseEntity<>(errorResponse(HttpStatus.BAD_REQUEST, message, null, request.getRequestURI()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalArgumentException(IllegalArgumentException ex, HttpServletRequest request) {
        logger.warn("Invalid argument for {} : {}", request.getRequestURI(), ex.getMessage());
        return new ResponseEntity<>(errorResponse(HttpStatus.BAD_REQUEST, ex.getMessage(), null, request.getRequestURI()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidSortException.class)
    public ResponseEntity<Map<String, Object>> handleInvalidSortException(InvalidSortException ex, HttpServletRequest request) {
        logger.warn("Invalid argument for {} : {}", request.getRequestURI(), ex.getMessage());
        return new ResponseEntity<>(errorResponse(HttpStatus.BAD_REQUEST, ex.getMessage(), null, request.getRequestURI()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DuplicateUserException.class)
    public ResponseEntity<Map<String, Object>> handleDuplicateUserException(DuplicateUserException ex, HttpServletRequest request) {
        logger.warn("Duplicate User at {} : {}", request.getRequestURI(), ex.getMessage());
        return new ResponseEntity<>(errorResponse(HttpStatus.CONFLICT, ex.getMessage(), null, request.getRequestURI()), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(DuplicateProjectException.class)
    public ResponseEntity<Map<String, Object>> handleDuplicateProjectException(DuplicateProjectException ex, HttpServletRequest request) {
        logger.warn("Duplicate Project at {} : {}", request.getRequestURI(), ex.getMessage());
        return new ResponseEntity<>(errorResponse(HttpStatus.CONFLICT, ex.getMessage(), null, request.getRequestURI()), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(DuplicateTaskException.class)
    public ResponseEntity<Map<String, Object>> handleDuplicateTaskException(DuplicateTaskException ex, HttpServletRequest request) {
        logger.warn("Duplicate Task at {} : {}", request.getRequestURI(), ex.getMessage());
        return new ResponseEntity<>(errorResponse(HttpStatus.CONFLICT, ex.getMessage(), null, request.getRequestURI()), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleResourceNotFoundException(ResourceNotFoundException ex, HttpServletRequest request) {
        logger.warn("Resource not found for {} : {}", request.getRequestURI(), ex.getMessage());
        return new ResponseEntity<>(errorResponse(HttpStatus.NOT_FOUND, ex.getMessage(), null, request.getRequestURI()), HttpStatus.NOT_FOUND);
    }

    private Map<String, Object> errorResponse(HttpStatus status, String message, Object errors, String path) {
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", status.value());
        response.put("error", status.getReasonPhrase());
        response.put("message", message);
        response.put("path", path);
        if (errors != null) {
            response.put("errors", errors);
        }
        return response;
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleException(Exception ex, HttpServletRequest request) {
        logger.error("Unexcepted error at {} : {}", request.getRequestURI(), ex.getMessage());
        return new ResponseEntity<>(errorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), null, request.getRequestURI()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
