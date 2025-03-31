package com.rhacp.movie_app_api.repositories;

import com.rhacp.movie_app_api.models.entities.MovieList;
import com.rhacp.movie_app_api.models.entities.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovieListRepository extends JpaRepository<MovieList, Long> {

    MovieList findByName(String name);

    MovieList findMovieListById(Long id);

    List<MovieList> findMovieListByUserMovieList(User user);
}
