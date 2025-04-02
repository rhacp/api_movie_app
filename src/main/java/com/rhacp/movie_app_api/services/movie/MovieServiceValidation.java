package com.rhacp.movie_app_api.services.movie;

import com.rhacp.movie_app_api.exceptions.ResourceNotFoundException;
import com.rhacp.movie_app_api.models.entities.Movie;

public interface MovieServiceValidation {

    /**
     * Search for Movie with the specified id and returns it if it exists.
     *
     * @param id Id to search for.
     * @param methodName Caller.
     * @return Movie.
     * @throws ResourceNotFoundException if Movie not found.
     */
    Movie getValidMovie(Long id, String methodName);
}
