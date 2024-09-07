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

    @Override
    public MovieDTO getMovieByTitle(String movieTitle) {
        Movie movie = movieServiceValidation.getValidMovie(movieTitle, "getMovieByTitle");

        return modelMapper.map(movie, MovieDTO.class);
    }

    @Override
    public void assignSearchIndex(List<Movie> movieList, SearchIndex searchIndex) {
        movieList.forEach(movie -> movie.setSearchIndex(searchIndex));
        movieList.forEach(this::saveMovie);
    }

    @Transactional
    @Override
    public void saveMovie(Movie movie) {
        Movie retrievedMovie = movieRepository.findMovieByTitle(movie.getTitle());
        if (retrievedMovie == null) {
            Movie savedMovie = movieRepository.save(movie);
            movie.setId(savedMovie.getId());
        } else {
            movie.setId(retrievedMovie.getId());
            movie.setTitle(retrievedMovie.getTitle());
            movie.setOverview(retrievedMovie.getOverview());
            movie.setPosterPath(retrievedMovie.getPosterPath());
            movie.setMovieId(retrievedMovie.getMovieId());
        }
    }

    @Override
    public boolean compareListsIdentical(List<Movie> movieList, List<Movie> lastMovieList) {
        if (movieList.isEmpty() || lastMovieList.isEmpty() || movieList.size() != lastMovieList.size()) {
            return false;
        }

        for (int i = 0; i < movieList.size(); i++) {
            if (!movieList.get(i).getMovieId().equals(lastMovieList.get(i).getMovieId())) {
                return false;
            }
        }

        return true;
    }

    @Override
    public void completeMovieListImagePath(List<Movie> movieList, String imagePath) {

    }
}
