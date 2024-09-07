package com.rhacp.movie_app_api.services.movie;

import com.rhacp.movie_app_api.exceptions.ResourceNotFoundException;
import com.rhacp.movie_app_api.models.entities.Movie;

public interface MovieServiceValidation {

    /**
     * Search for a review with the specified id and returns it.
     *
     * @param movieTitle Movie title to search for.
     * @param methodName Method name.
     * @return Movie found.
     * @throws ResourceNotFoundException if review not found.
     */
    Movie getValidMovie(String movieTitle, String methodName);

    Movie validateMovieAlreadyExists(Movie movie);
}
