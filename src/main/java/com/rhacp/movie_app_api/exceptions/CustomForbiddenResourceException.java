package com.rhacp.movie_app_api.exceptions;

public class CustomForbiddenResourceException extends RuntimeException {

    public CustomForbiddenResourceException(String message) {
        super(message);
    }
}
