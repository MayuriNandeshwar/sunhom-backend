package com.sunhom.common.handler;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // ===============================
    // BAD REQUEST (400)
    // ===============================
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleIllegalArgument(
            IllegalArgumentException ex,
            HttpServletRequest request) {

        return buildResponse(
                HttpStatus.BAD_REQUEST,
                ex.getMessage(),
                request.getRequestURI());
    }

    // ===============================
    // VALIDATION ERRORS (400)
    // ===============================
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidation(
            MethodArgumentNotValidException ex,
            HttpServletRequest request) {

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors()
                .forEach(err -> errors.put(err.getField(), err.getDefaultMessage()));

        return buildResponse(
                HttpStatus.BAD_REQUEST,
                "Validation failed",
                request.getRequestURI(),
                errors);
    }

    // ===============================
    // UNAUTHORIZED (401)
    // ===============================
    @ExceptionHandler(org.springframework.security.access.AccessDeniedException.class)
    public ResponseEntity<?> handleAccessDenied(
            Exception ex,
            HttpServletRequest request) {

        return buildResponse(
                HttpStatus.FORBIDDEN,
                "Access denied",
                request.getRequestURI());
    }

    // ===============================
    // GENERIC EXCEPTION (500)
    // ===============================
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGeneric(
            Exception ex,
            HttpServletRequest request) {

        ex.printStackTrace(); // 🔥 PRINT REAL ERROR

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of(
                        "error", ex.getMessage(),
                        "exception", ex.getClass().getName(),
                        "path", request.getRequestURI()));
    }

    // ===============================
    // RESPONSE BUILDER
    // ===============================
    private ResponseEntity<?> buildResponse(
            HttpStatus status,
            String message,
            String path) {

        return buildResponse(status, message, path, null);
    }

    private ResponseEntity<?> buildResponse(
            HttpStatus status,
            String message,
            String path,
            Object details) {

        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", OffsetDateTime.now());
        body.put("status", status.value());
        body.put("error", message);
        body.put("path", path);

        if (details != null) {
            body.put("details", details);
        }

        return new ResponseEntity<>(body, status);
    }
}