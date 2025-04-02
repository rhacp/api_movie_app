package com.rhacp.movie_app_api.services.movie_list;

import com.rhacp.movie_app_api.exceptions.ResourceAlreadyExistsException;
import com.rhacp.movie_app_api.exceptions.ResourceNotFoundException;
import com.rhacp.movie_app_api.models.dtos.MovieListDTO;
import com.rhacp.movie_app_api.models.entities.MovieList;

public interface MovieListValidation {

    /**
     * Check if MovieList already exists.
     *
     * @param movieListDTO MovieListDTO to check.
     * @throws ResourceAlreadyExistsException if Movie exists.
     */
    void validateMovieListAlreadyExists(MovieListDTO movieListDTO);

    /**
     * Search for MovieList with the specified id and returns it if it exists.
     *
     * @param id Id to search for.
     * @param methodName Caller.
     * @return MovieList.
     * @throws ResourceNotFoundException if Movie not found.
     */
    MovieList getValidMovieList(Long id, String methodName);
}
