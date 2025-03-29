package com.rhacp.movie_app_api.repositories;

import com.rhacp.movie_app_api.models.entities.MovieList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieListRepository extends JpaRepository<MovieList, Long> {

    MovieList findByName(String name);

    MovieList findMovieById(Long id);
}
