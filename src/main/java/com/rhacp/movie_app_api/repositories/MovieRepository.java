package com.rhacp.movie_app_api.repositories;

import com.rhacp.movie_app_api.models.entities.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

    /**
     * Query to retrieve movie by title from DB.
     *
     * @param movieId Title of the movie.
     * @return Movie.
     */
    Movie findMovieByMovieId(Long movieId);

    Movie findMovieById(Long id);
}
