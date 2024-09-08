package com.rhacp.movie_app_api.exceptions;

public class ResourceAlreadyExists extends RuntimeException {

    public ResourceAlreadyExists(String message) { super(message); }
}
