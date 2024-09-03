package com.rhacp.movie_app_api.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

        log.info("MethodArgumentNotValidException thrown from DTO validation.");
        return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ReviewNotFoundException.class)
    public ResponseEntity<Object> handleReviewNotFoundException(ReviewNotFoundException exception) {
        log.info("ReviewNotFoundException thrown");
        return getResponse(exception, HttpStatus.NOT_FOUND);
    }

    private ResponseEntity<Object> getResponse(RuntimeException exception, HttpStatus httpStatus) {
        Map<String, Object> result = new HashMap<>();
        result.put("message", exception.getMessage());
        return new ResponseEntity<>(result, httpStatus);
    }
}
