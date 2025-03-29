package com.rhacp.movie_app_api.services.movie_list;

import com.rhacp.movie_app_api.models.dtos.MovieListDTO;
import com.rhacp.movie_app_api.models.entities.MovieList;
import com.rhacp.movie_app_api.repositories.MovieListRepository;
import com.rhacp.movie_app_api.services.user.UserService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;

@Slf4j
@Service
public class MovieListServiceImpl implements MovieListService {

    private final MovieListRepository movieListRepository;

    private final ModelMapper modelMapper;

    private final MovieListValidation movieListValidation;

    private final UserService userService;

    public MovieListServiceImpl(MovieListRepository movieListRepository, ModelMapper modelMapper, MovieListValidation movieListValidation, UserService userService) {
        this.movieListRepository = movieListRepository;
        this.modelMapper = modelMapper;
        this.movieListValidation = movieListValidation;
        this.userService = userService;
    }

    @Transactional
    @Override
    public MovieListDTO createMovieList(MovieListDTO movieListDTO, String token) {
        movieListValidation.validateMovieListAlreadyExists(movieListDTO);

        MovieList movieList = modelMapper.map(movieListDTO, MovieList.class);
        movieList.setDate(LocalDate.now());
        movieList.setTime(LocalTime.now().withNano(0));
        movieList.setUserMovieList(userService.getUserByToken(token));
        MovieList savedMovieList = movieListRepository.save(movieList);
        log.info("MovieList {} inserted in db. Method: {}.", savedMovieList.getId(), "createMovieList");

        return modelMapper.map(savedMovieList, MovieListDTO.class);
    }

    @Transactional
    @Override
    public void deleteMovieListById(Long movieListId, String token) {
        MovieList foundMovieList = movieListValidation.getValidMovieList(movieListId, "deleteMovieListById");

        userService.checkIfUserTheSame(userService.getUserByToken(token), foundMovieList.getUserMovieList());

        movieListRepository.deleteById(movieListId);
        log.info("MovieList {} deleted. Method {}.", movieListId, "deleteMovieListById");
    }
}
