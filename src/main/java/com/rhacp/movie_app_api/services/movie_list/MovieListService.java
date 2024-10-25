package com.rhacp.movie_app_api.services.movie_list;

import com.rhacp.movie_app_api.models.dtos.MovieListDTO;

public interface MovieListService {

    MovieListDTO createMovieList(MovieListDTO movieListDTO, String token);
}
