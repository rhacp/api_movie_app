package com.rhacp.movie_app_api.services.search_index;

import com.rhacp.movie_app_api.models.dtos.MovieDTO;

import java.util.List;

public interface SearchIndexService {

    /**
     * Returns the full movie list.
     *
     * @return Movie list.
     */
    List<MovieDTO> getMovieList(String keyword);
}
