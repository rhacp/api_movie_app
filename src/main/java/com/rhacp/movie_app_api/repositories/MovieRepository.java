package com.rhacp.movie_app_api.repositories;

import com.rhacp.movie_app_api.models.entities.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

    /**
     * Retrieve movie by id.
     *
     * @param id Title to search for.
     * @return Movie.
     */
    Movie findMovieById(Long id);
}
