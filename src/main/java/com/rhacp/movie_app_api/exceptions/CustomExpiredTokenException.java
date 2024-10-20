package com.rhacp.movie_app_api.exceptions;

public class CustomExpiredTokenException extends RuntimeException {
    public CustomExpiredTokenException(String message) { super(message); }
}
