package com.rhacp.movie_app_api.exceptions;

public class CustomSignatureMismatch extends RuntimeException {

    public CustomSignatureMismatch(String message) { super(message); }
}
