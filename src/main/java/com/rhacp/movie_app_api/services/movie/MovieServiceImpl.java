package com.rhacp.movie_app_api.services.movie;

import com.rhacp.movie_app_api.models.dtos.MovieDTO;
import com.rhacp.movie_app_api.models.entities.Movie;
import com.rhacp.movie_app_api.models.entities.SearchIndex;
import com.rhacp.movie_app_api.repositories.MovieRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;

    private final MovieServiceValidation movieServiceValidation;

    private final ModelMapper modelMapper;

    public MovieServiceImpl(MovieRepository movieRepository, MovieServiceValidation movieServiceValidation, ModelMapper modelMapper) {
        this.movieRepository = movieRepository;
        this.movieServiceValidation = movieServiceValidation;
        this.modelMapper = modelMapper;
    }

    public MovieDTO getMovieById(Long id) {
        Movie movie = movieServiceValidation.getValidMovie(id, "getMovieById");
        return modelMapper.map(movie, MovieDTO.class);
    }

    @Override
    public void assignSearchIndex(List<Movie> movieList, SearchIndex searchIndex) {
        movieList.forEach(movie -> movie.setSearchIndex(searchIndex));
    }

    @Transactional
    @Override
    public void saveMovieList(List<Movie> movieList) {
        movieList.forEach(movie -> {
            Movie retrievedMovie = movieRepository.findMovieByMovieId(movie.getMovieId());
            if (retrievedMovie == null) {
                Movie savedMovie = movieRepository.save(movie);
                movie.setId(savedMovie.getId());
            } else {
                movie.setId(retrievedMovie.getId());
                movie.setTitle(retrievedMovie.getTitle());
                movie.setOverview(retrievedMovie.getOverview());
                movie.setPosterPath(retrievedMovie.getPosterPath());
                movie.setMovieId(retrievedMovie.getMovieId());
                movieRepository.save(movie);
            }
        });
    }
}
