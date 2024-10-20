package com.rhacp.movie_app_api.exceptions;

public class ResourceAlreadyExistsException extends RuntimeException {

    public ResourceAlreadyExistsException(String message) { super(message); }
}
