package com.rhacp.movie_app_api.repositories;

import com.rhacp.movie_app_api.models.entities.SearchIndex;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SearchIndexRepository extends JpaRepository<SearchIndex, Long> {

    /**
     * Retrieve SearchIndexes by keyword.
     *
     * @param keyword Keyword to search for.
     * @return List of SearchIndex.
     */
    List<SearchIndex> findByKeyword(String keyword);
}
