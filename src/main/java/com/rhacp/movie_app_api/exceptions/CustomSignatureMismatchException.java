package com.rhacp.movie_app_api.exceptions;

public class CustomSignatureMismatchException extends RuntimeException {

    public CustomSignatureMismatchException(String message) { super(message); }
}
