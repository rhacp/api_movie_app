package com.rhacp.movie_app_api.services.movie;

import com.rhacp.movie_app_api.exceptions.ResourceNotFoundException;
import com.rhacp.movie_app_api.models.entities.Movie;

public interface MovieServiceValidation {

    /**
     * Search for a review with the specified id and returns it.
     *
     * @param id MovieId title to search for.
     * @param methodName Method name.
     * @return Movie found.
     * @throws ResourceNotFoundException if review not found.
     */
    Movie getValidMovie(Long id, String methodName);

    Movie validateMovieAlreadyExists(Movie movie);
}
