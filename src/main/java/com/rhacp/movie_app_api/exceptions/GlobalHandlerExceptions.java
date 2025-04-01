package com.rhacp.movie_app_api.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * Global controller for handling all exceptions and return the error messages in the API response.
 */
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

    @ExceptionHandler(CustomSignatureMismatchException.class)
    public ResponseEntity<Object> handleCustomSignatureMismatch(CustomSignatureMismatchException exception) {
        log.error("CustomSignatureMismatch thrown: {}", exception.getMessage());
        return getResponse(new RuntimeException(exception.getMessage()), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(CustomExpiredTokenException.class)
    public ResponseEntity<Object> handleCustomExpiredTokenException(CustomExpiredTokenException exception) {
        log.error("CustomExpiredTokenException thrown: {}", exception.getMessage());
        return getResponse(new RuntimeException(exception.getMessage()), HttpStatus.UNAUTHORIZED);
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

    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ResponseEntity<Object> handleResourceAlreadyExists(ResourceAlreadyExistsException exception) {
        log.warn("ResourceAlreadyExists thrown.");
        return getResponse(exception, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(CustomForbiddenResourceException.class)
    public ResponseEntity<Object> handleCustomForbiddenResourceException(CustomForbiddenResourceException exception) {
        log.warn("CustomForbiddenResourceException thrown.");
        return getResponse(exception, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<Object> handleUsernameNotFoundException(UsernameNotFoundException exception) {
        log.warn("UsernameNotFoundException thrown.");
        return getResponse(exception, HttpStatus.NOT_FOUND);
    }

    private ResponseEntity<Object> getResponse(RuntimeException exception,
                                               HttpStatus httpStatus) {
        Map<String, Object> result = new HashMap<>();
        result.put("message", exception.getMessage());
        return new ResponseEntity<>(result, httpStatus);
    }
}
