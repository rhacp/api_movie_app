package com.rhacp.movie_app_api.services.search_index;

import com.rhacp.movie_app_api.models.dtos.MovieDTO;

import java.util.List;

public interface SearchIndexService {

    /**
     * Returns the movie list assigned to the SearchIndex found by keyword.
     *
     * @return Movie list.
     */
    List<MovieDTO> getSearchIndex(String keyword);
}
