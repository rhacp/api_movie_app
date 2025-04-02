package com.rhacp.movie_app_api.services.movie_list;

import com.rhacp.movie_app_api.exceptions.ResourceAlreadyExistsException;
import com.rhacp.movie_app_api.exceptions.ResourceNotFoundException;
import com.rhacp.movie_app_api.models.dtos.MovieListDTO;
import com.rhacp.movie_app_api.models.entities.MovieList;
import com.rhacp.movie_app_api.repositories.MovieListRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MovieListValidationImpl implements MovieListValidation {

    private final MovieListRepository movieListRepository;

    public MovieListValidationImpl(MovieListRepository movieListRepository) {
        this.movieListRepository = movieListRepository;
    }

    @Transactional
    @Override
    public void validateMovieListAlreadyExists(MovieListDTO movieListDTO) {
        MovieList movieListFound = movieListRepository.findByName(movieListDTO.getName());

        if (movieListFound != null) {
            throw new ResourceAlreadyExistsException("A movieList with the name " + movieListDTO.getName() + " already exists.");
        }
    }

    @Transactional
    @Override
    public MovieList getValidMovieList(Long id,
                                       String methodName) {
        MovieList movieListFound = movieListRepository.findMovieListById(id);

        if (movieListFound == null) {
            throw new ResourceNotFoundException("MovieList with id " + id + " not found.");
        }

        log.info("MovieList with id {} retrieved from db. Method: {}", id, methodName);

        return movieListFound;
    }
}
