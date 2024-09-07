package com.rhacp.movie_app_api.services.movie;

import com.rhacp.movie_app_api.models.dtos.MovieDTO;
import com.rhacp.movie_app_api.models.entities.Movie;
import com.rhacp.movie_app_api.models.entities.SearchIndex;

import java.util.List;

public interface MovieService {

    /**
     * Return a review based on the given id.
     *
     * @param movieTitle Movie title to search for.
     * @return ReviewDTO with the specified id.
     */
    MovieDTO getMovieByTitle(String movieTitle);

    /**
     * Helper function that assigns the given indexSearch to all movies in the list and saves them in db.
     *
     * @param movieList Given movie list.
     * @param searchIndex IndexSearch to assign.
     */
    void assignSearchIndex(List<Movie> movieList, SearchIndex searchIndex);

    void saveMovie(Movie movie);

    /**
     * Helper function that checks if the movie lists are the same besides the id and indexSearch.
     *
     * @param movieList First movie list.
     * @param lastMovieList Second movie list.
     * @return boolean value.
     */
    boolean compareListsIdentical(List<Movie> movieList, List<Movie> lastMovieList);

    void completeMovieListImagePath(List<Movie> movieList, String imagePath);
}
