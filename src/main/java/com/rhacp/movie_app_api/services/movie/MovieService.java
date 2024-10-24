package com.rhacp.movie_app_api.services.movie;

import com.rhacp.movie_app_api.models.dtos.MovieDTO;
import com.rhacp.movie_app_api.models.entities.Movie;
import com.rhacp.movie_app_api.models.entities.SearchIndex;

import java.util.List;

public interface MovieService {

    /**
     * Return a movie based on the given id.
     *
     * @param id MovieId to search for.
     * @return ReviewDTO with the specified id.
     */
    MovieDTO getMovieById(Long id);

    /**
     * Helper function that assigns the given indexSearch to all movies in the list and saves them in db.
     *
     * @param movieList Given movie list.
     * @param searchIndex IndexSearch to assign.
     */
    void assignSearchIndex(List<Movie> movieList, SearchIndex searchIndex);

    void saveMovieList(List<Movie> movieList);
}
