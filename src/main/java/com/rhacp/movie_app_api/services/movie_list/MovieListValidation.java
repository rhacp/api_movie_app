package com.rhacp.movie_app_api.services.movie_list;

import com.rhacp.movie_app_api.models.dtos.MovieListDTO;
import com.rhacp.movie_app_api.models.entities.MovieList;

public interface MovieListValidation {

    void validateMovieListAlreadyExists(MovieListDTO movieDTO);

    public MovieList getValidMovieList(Long movieListId, String methodName);
}
