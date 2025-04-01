package com.rhacp.movie_app_api.services.movie_list;

import com.rhacp.movie_app_api.models.dtos.MovieListDTO;

import java.util.List;
import java.util.Map;

public interface MovieListService {

    MovieListDTO createMovieList(MovieListDTO movieListDTO, String token);

    List<MovieListDTO> getAllMovieLists();

    MovieListDTO getMovieListById(Long id, String token);

    MovieListDTO updateMovieListById(Long id, MovieListDTO movieListDTO, String token);

    Map<String, String> deleteMovieListById(Long id, String token);

    MovieListDTO addMovieToListById(Long movieListId, Long movieId, String token);

    MovieListDTO deleteMovieFromListById(Long movieListId, Long movieId, String token);

    List<MovieListDTO> getAllMovieListsForUser(Long userId, String token);
}
