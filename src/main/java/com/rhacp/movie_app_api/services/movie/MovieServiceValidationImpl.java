package com.rhacp.movie_app_api.services.movie;

import com.rhacp.movie_app_api.exceptions.ResourceAlreadyExistsException;
import com.rhacp.movie_app_api.exceptions.ResourceNotFoundException;
import com.rhacp.movie_app_api.models.entities.Movie;
import com.rhacp.movie_app_api.repositories.MovieRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MovieServiceValidationImpl implements MovieServiceValidation {

    private final MovieRepository movieRepository;

    public MovieServiceValidationImpl(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Transactional
    @Override
    public Movie getValidMovie(String movieTitle, String methodName) {
        Movie movie = movieRepository.findMovieByTitle(movieTitle);

        if (movie == null) {
            throw new ResourceNotFoundException("Movie with the title \"" + movieTitle + "\" not found.");
        }
        log.info("Movie with the title {} retrieved. Method: {}", movieTitle, methodName);

        return movie;
    }

    @Override
    public Movie validateMovieAlreadyExists(Movie movie) {
        Movie foundMovie = movieRepository.findMovieByTitle(movie.getTitle());

        if (foundMovie == null) {
            throw new ResourceAlreadyExistsException("Movie with the title \"" + movie.getTitle() + "\" already exists. Will not be saved in DB.");
        }

        return movie;
    }
}
