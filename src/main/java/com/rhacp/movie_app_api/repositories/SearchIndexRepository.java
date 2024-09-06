package com.rhacp.movie_app_api.repositories;

import com.rhacp.movie_app_api.models.entities.SearchIndex;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SearchIndexRepository extends JpaRepository<SearchIndex, Long> {

    SearchIndex findMovieListById(Long id);

    SearchIndex findFirstByOrderByIdDesc();
}
