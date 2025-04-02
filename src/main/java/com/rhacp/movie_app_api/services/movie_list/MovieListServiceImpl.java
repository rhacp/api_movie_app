package com.rhacp.movie_app_api.services.movie_list;

import com.rhacp.movie_app_api.models.dtos.movie_list.MovieListDTO;
import com.rhacp.movie_app_api.models.dtos.movie_list.MovieListInputDTO;
import com.rhacp.movie_app_api.models.entities.Movie;
import com.rhacp.movie_app_api.models.entities.MovieList;
import com.rhacp.movie_app_api.models.entities.user.User;
import com.rhacp.movie_app_api.repositories.MovieListRepository;
import com.rhacp.movie_app_api.services.movie.MovieService;
import com.rhacp.movie_app_api.services.user.UserService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class MovieListServiceImpl implements MovieListService {

    private final MovieListRepository movieListRepository;

    private final ModelMapper modelMapper;

    private final MovieListValidation movieListValidation;

    private final UserService userService;

    private final MovieService movieService;

    public MovieListServiceImpl(MovieListRepository movieListRepository, ModelMapper modelMapper, MovieListValidation movieListValidation, UserService userService, MovieService movieService) {
        this.movieListRepository = movieListRepository;
        this.modelMapper = modelMapper;
        this.movieListValidation = movieListValidation;
        this.userService = userService;
        this.movieService = movieService;
    }

    @Transactional
    @Override
    public MovieListDTO createMovieList(MovieListInputDTO movieListInputDTO, String token) {
        movieListValidation.validateMovieListAlreadyExists(movieListInputDTO);

        MovieList movieList = modelMapper.map(movieListInputDTO, MovieList.class);
        movieList.setCreationDate(LocalDate.now());
        movieList.setCreationTime(LocalTime.now().withNano(0));
        movieList.setUserMovieList(userService.getUserByToken(token));

        MovieList savedMovieList = movieListRepository.save(movieList);
        log.info("MovieList {} inserted in db. Method: {}.", savedMovieList.getId(), "createMovieList");

        return modelMapper.map(savedMovieList, MovieListDTO.class);
    }

    @Transactional
    @Override
    public List<MovieListDTO> getAllMovieLists() {
        List<MovieList> movieListList = movieListRepository.findAll();
        log.info("MovieList list retrieved from db. Method: {}.", "getAllMovieLists");

        return movieListList.stream()
                .map(movieList -> modelMapper.map(movieList, MovieListDTO.class))
                .toList();
    }

    @Override
    public MovieListDTO getMovieListById(Long id, String token) {
        MovieList movieList = movieListValidation.getValidMovieList(id, "getMovieListById");

        userService.checkIfUserTheSame(userService.getUserByToken(token), movieList.getUserMovieList());

        return modelMapper.map(movieList, MovieListDTO.class);
    }

    @Transactional
    @Override
    public MovieListDTO updateMovieListById(Long id, MovieListInputDTO movieListInputDTO, String token) {
        MovieList movieList = movieListValidation.getValidMovieList(id, "updateMovieListById");
        userService.checkIfUserTheSame(userService.getUserByToken(token), movieList.getUserMovieList());

        updateMovieListFromDTO(movieList, movieListInputDTO);
        MovieList savedMovieList = movieListRepository.save(movieList);
        log.info("MovieList {} updated. Method: {}.", savedMovieList.getId(), "updateMovieListById");

        return modelMapper.map(savedMovieList, MovieListDTO.class);
    }

    @Transactional
    @Override
    public Map<String, String> deleteMovieListById(Long id, String token) {
        MovieList foundMovieList = movieListValidation.getValidMovieList(id, "deleteMovieListById");
        userService.checkIfUserTheSame(userService.getUserByToken(token), foundMovieList.getUserMovieList());

        movieListRepository.deleteById(id);
        log.info("MovieList {} deleted. Method {}.", id, "deleteMovieListById");

        Map<String, String> response = new HashMap<>();
        response.put("message", "MovieList with id " + id + " deleted.");

        return response;
    }

    @Transactional
    @Override
    public MovieListDTO addMovieToListById(Long movieListId,
                                           Long movieId,
                                           String token) {
        MovieList foundMovieList = movieListValidation.getValidMovieList(movieListId, "addMovieToListById");
        userService.checkIfUserTheSame(userService.getUserByToken(token), foundMovieList.getUserMovieList());

        Movie foundMovie = movieService.setMovieListAndReturnMovieById(movieId, foundMovieList);

        foundMovieList.getMovies().add(foundMovie);
        MovieList savedMovieList = movieListRepository.save(foundMovieList);
        log.info("Movie {} added to MovieList {}. Method: {}.", savedMovieList.getId(), foundMovie.getMovieId(), "addMovieToListById");

        return modelMapper.map(savedMovieList, MovieListDTO.class);
    }

    @Transactional
    @Override
    public MovieListDTO deleteMovieFromListById(Long movieListId,
                                                Long movieId,
                                                String token) {
        MovieList foundMovieList = movieListValidation.getValidMovieList(movieListId, "deleteMovieFromListById");
        userService.checkIfUserTheSame(userService.getUserByToken(token), foundMovieList.getUserMovieList());

        Movie foundMovie = movieService.removeMovieListAndReturnMovieById(movieId, foundMovieList);

        foundMovieList.getMovies().remove(foundMovie);
        MovieList savedMovieList = movieListRepository.save(foundMovieList);
        log.info("Movie {} removed from MovieList {}. Method: {}.", savedMovieList.getId(), foundMovie.getMovieId(), "deleteMovieFromListById");

        return modelMapper.map(savedMovieList, MovieListDTO.class);
    }

    @Transactional
    @Override
    public List<MovieListDTO> getAllMovieListsForUser(Long userId,
                                                      String token) {
        User foundUser = userService.getUserEntityById(userId);
        userService.checkIfUserTheSame(userService.getUserByToken(token), foundUser);

        List<MovieList> movieListList = movieListRepository.findMovieListByUserMovieList(foundUser);

        return movieListList.stream()
                .map(movieList -> modelMapper.map(movieList, MovieListDTO.class))
                .toList();
    }

    private void updateMovieListFromDTO(MovieList movieList,
                                        MovieListInputDTO movieListDTO) {
        if (movieListDTO.getName() != null) {
            movieList.setName(movieListDTO.getName());
        }

        if (movieListDTO.getDescription() != null) {
            movieList.setDescription(movieListDTO.getDescription());
        }
    }
}
