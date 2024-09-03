package com.rhacp.movie_app_api.exceptions;

public class ReviewNotFoundException extends RuntimeException {

    public ReviewNotFoundException(String message) {
        super(message);
    }
}
