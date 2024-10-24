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
    public Movie getValidMovie(Long id, String methodName) {
        Movie movie = movieRepository.findMovieById(id);

        if (movie == null) {
            throw new ResourceNotFoundException("Movie with the id \"" + id + "\" not found.");
        }
        log.info("Movie with the title {} retrieved. Method: {}", id, methodName);

        return movie;
    }

    @Override
    public Movie validateMovieAlreadyExists(Movie movie) {
        Movie foundMovie = movieRepository.findMovieById(movie.getId());

        if (foundMovie == null) {
            throw new ResourceAlreadyExistsException("Movie with the title \"" + movie.getId() + "\" already exists. Will not be saved in DB.");
        }

        return movie;
    }
}
