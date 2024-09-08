package com.rhacp.movie_app_api.exceptions;

import io.jsonwebtoken.MalformedJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalHandlerExceptions {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        Map<String, Object> result = new HashMap<>();

        exception.getBindingResult().getFieldErrors()
                .forEach(error -> result.put(error.getField(), error.getDefaultMessage()));

        log.error("MethodArgumentNotValidException thrown from DTO validation.");
        return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MalformedJwtException.class)
    public ResponseEntity<Object> handleSecurityException(MalformedJwtException exception) {
        log.error("MalformedJwtException thrown: {}", exception.getMessage());
        return getResponse(new RuntimeException("Authentication header is missing or incorrect."), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(CustomSignatureMismatch.class)
    public ResponseEntity<Object> handleCustomSignatureMismatch(CustomSignatureMismatch exception) {
        log.error("CustomSignatureMismatch thrown: {}", exception.getMessage());
        return getResponse(new RuntimeException("Authentication header is missing or incorrect."), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Object> handleBadCredentialsException(BadCredentialsException exception) {
        log.error("BadCredentialsException thrown.");
        return getResponse(exception, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException exception) {
        log.error("ResourceNotFoundException thrown.");
        return getResponse(exception, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ResourceAlreadyExists.class)
    public ResponseEntity<Object> handleResourceAlreadyExists(ResourceAlreadyExists exception) {
        log.warn("ResourceAlreadyExists thrown.");
        return getResponse(exception, HttpStatus.CONFLICT);
    }

    private ResponseEntity<Object> getResponse(RuntimeException exception, HttpStatus httpStatus) {
        Map<String, Object> result = new HashMap<>();
        result.put("message", exception.getMessage());
        return new ResponseEntity<>(result, httpStatus);
    }
}
