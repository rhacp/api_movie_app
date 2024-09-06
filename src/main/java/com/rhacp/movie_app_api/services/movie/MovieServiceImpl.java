package com.rhacp.movie_app_api.services.movie;

import com.rhacp.movie_app_api.models.entities.Movie;
import com.rhacp.movie_app_api.models.entities.SearchIndex;
import com.rhacp.movie_app_api.repositories.MovieRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;

    public MovieServiceImpl(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Override
    public void assignSearchIndexAndSave(List<Movie> movieList, SearchIndex searchIndex) {
        movieList.forEach(movie -> movie.setSearchIndex(searchIndex));
        movieRepository.saveAll(movieList);
    }

    @Override
    public boolean compareListsIdentical(List<Movie> movieList, List<Movie> lastMovieList) {
        if (movieList.isEmpty() || lastMovieList.isEmpty()) {
            return false;
        }

        for (int i = 0; i < movieList.size(); i++) {
            if (!movieList.get(i).getOverview().equals(lastMovieList.get(i).getOverview())
                    || !movieList.get(i).getTitle().equals(lastMovieList.get(i).getTitle())
                    || !movieList.get(i).getPosterPath().equals(lastMovieList.get(i).getPosterPath())) {
                return false;
            }
        }

        return true;
    }


}
