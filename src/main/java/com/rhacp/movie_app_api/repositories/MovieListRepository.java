package com.rhacp.movie_app_api.repositories;

import com.rhacp.movie_app_api.models.entities.MovieList;
import com.rhacp.movie_app_api.models.entities.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovieListRepository extends JpaRepository<MovieList, Long> {

    /**
     * Retrieve MovieList by name.
     *
     * @param name Name to search for.
     * @return MovieList.
     */
    MovieList findByName(String name);

    /**
     * Retrieve MovieList by id.
     *
     * @param id Id to search for.
     * @return MovieList.
     */
    MovieList findMovieListById(Long id);

    /**
     * Retrieve MovieLists by User.
     *
     * @param user User to search for.
     * @return List of MovieList.
     */
    List<MovieList> findMovieListByUserMovieList(User user);
}
