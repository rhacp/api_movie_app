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
    public void validateMovieListAlreadyExists(MovieListDTO movieDTO) {
        MovieList movieListFound = movieListRepository.findByName(movieDTO.getName());

        if (movieListFound != null) {
            throw new ResourceAlreadyExistsException("A movieList with the name " + movieDTO.getName() + " already exists.");
        }
    }

    @Transactional
    @Override
    public MovieList getValidMovieList(Long movieListId,
                                       String methodName) {
        MovieList movieListFound = movieListRepository.findMovieListById(movieListId);

        if (movieListFound == null) {
            throw new ResourceNotFoundException("MovieList with id " + movieListId + " not found.");
        }

        log.info("MovieList with id {} retrieved from db. Method: {}", movieListId, methodName);

        return movieListFound;
    }
}
