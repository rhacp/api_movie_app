package com.rhacp.movie_app_api.services.movie;

import com.rhacp.movie_app_api.models.entities.Movie;
import com.rhacp.movie_app_api.models.entities.SearchIndex;

import java.util.List;

public interface MovieService {

    void assignSearchIndexAndSave(List<Movie> movieList, SearchIndex searchIndex);

    boolean compareListsIdentical(List<Movie> movieList, List<Movie> lastMovieList);
}
