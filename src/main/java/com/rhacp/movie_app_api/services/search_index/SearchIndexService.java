package com.rhacp.movie_app_api.services.search_index;

import com.rhacp.movie_app_api.models.dtos.MovieDTO;
import com.rhacp.movie_app_api.models.entities.Movie;

import java.util.List;

public interface SearchIndexService {

    /**
     * Returns the full movie list.
     */
    List<MovieDTO> getMovieList();

    /**
     * Helper function that executes the call to the movie API to retrieve the movie list.
     *
     * @param url Movies API url.
     */
    List<Movie> makeCallToMoviesApi(String url);
}
