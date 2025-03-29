package com.rhacp.movie_app_api.services.movie_list;

import com.rhacp.movie_app_api.models.dtos.MovieListDTO;

import java.util.List;

public interface MovieListService {

    MovieListDTO createMovieList(MovieListDTO movieListDTO, String token);

    List<MovieListDTO> getAllMovieLists();

    MovieListDTO getMovieListById(Long id, String token);

    void deleteMovieListById(Long movieListId, String token);
}
