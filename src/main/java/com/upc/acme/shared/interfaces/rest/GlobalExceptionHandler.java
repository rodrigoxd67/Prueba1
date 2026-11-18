package com.upc.acme.shared.interfaces.rest;

import com.upc.acme.workshops.domain.exceptions.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    private final MessageSource messages;

    public GlobalExceptionHandler(MessageSource messages) { this.messages = messages; }

    private ResponseEntity<ApiErrorResource> error(HttpStatus status, String key, Map<String, String> fields, HttpServletRequest request) {
        var locale = LocaleContextHolder.getLocale();
        return ResponseEntity.status(status).header(HttpHeaders.CONTENT_LANGUAGE, locale.toLanguageTag()).body(new ApiErrorResource(
                Instant.now(), status.value(), messages.getMessage(key, null, locale), request.getRequestURI(), fields));
    }

    @ExceptionHandler(WorkshopRuleException.class)
    public ResponseEntity<ApiErrorResource> invalid(WorkshopRuleException exception, HttpServletRequest request) {
        return error(HttpStatus.BAD_REQUEST, exception.getMessage(), Map.of(), request);
    }

    @ExceptionHandler(DuplicateWorkshopException.class)
    public ResponseEntity<ApiErrorResource> duplicate(DuplicateWorkshopException exception, HttpServletRequest request) {
        return error(HttpStatus.CONFLICT, exception.getMessage(), Map.of(), request);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResource> validation(MethodArgumentNotValidException exception, HttpServletRequest request) {
        Map<String, String> fields = new LinkedHashMap<>();
        exception.getBindingResult().getFieldErrors().forEach(e -> fields.putIfAbsent(e.getField(), e.getDefaultMessage()));
        return error(HttpStatus.BAD_REQUEST, "request.invalid", fields, request);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiErrorResource> malformed(HttpServletRequest request) {
        return error(HttpStatus.BAD_REQUEST, "request.invalid", Map.of(), request);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ApiErrorResource> method(HttpRequestMethodNotSupportedException exception, HttpServletRequest request) {
        var response = error(HttpStatus.METHOD_NOT_ALLOWED, "method.invalid", Map.of(), request);
        return ResponseEntity.status(response.getStatusCode()).headers(response.getHeaders())
                .allow(exception.getSupportedHttpMethods().toArray(HttpMethod[]::new)).body(response.getBody());
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<ApiErrorResource> media(HttpServletRequest request) {
        return error(HttpStatus.UNSUPPORTED_MEDIA_TYPE, "media.invalid", Map.of(), request);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResource> unexpected(Exception exception, HttpServletRequest request) {
        LOGGER.error("Unexpected API failure: {}", exception.getClass().getSimpleName());
        return error(HttpStatus.INTERNAL_SERVER_ERROR, "server.error", Map.of(), request);
    }
}
