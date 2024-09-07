package com.rhacp.movie_app_api.repositories;

import com.rhacp.movie_app_api.models.entities.SearchIndex;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SearchIndexRepository extends JpaRepository<SearchIndex, Long> {

    /**
     * Query to retrieve the searchIndex by id.
     *
     * @param id Id of the searchIndex.
     * @return searchIndex.
     */
    SearchIndex findSearchIndexById(Long id);

    /**
     * Query to retrieve the searchIndex with the highest id.
     *.
     * @return searchIndex.
     */
    SearchIndex findFirstByOrderByIdDesc();

    List<SearchIndex> findByKeyword(String keyword);
}
