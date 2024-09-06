package com.rhacp.movie_app_api.repositories;

import com.rhacp.movie_app_api.models.entities.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Long> {

}
