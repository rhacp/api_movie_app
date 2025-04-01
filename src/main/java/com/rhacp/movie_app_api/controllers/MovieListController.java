package com.rhacp.movie_app_api.controllers;

import com.rhacp.movie_app_api.models.dtos.MovieListDTO;
import com.rhacp.movie_app_api.services.movie_list.MovieListService;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/movieList")
public class MovieListController {

    private final MovieListService movieListService;

    public MovieListController(MovieListService movieListService) {
        this.movieListService = movieListService;
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<MovieListDTO> createMovieList(@Valid @RequestBody MovieListDTO movieListDTO, @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        return ResponseEntity.ok(movieListService.createMovieList(movieListDTO, token));
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<List<MovieListDTO>> getAllMovieLists() {
        return ResponseEntity.ok(movieListService.getAllMovieLists());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<MovieListDTO> getMovieListById(@PathVariable("id") Long id, @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        return ResponseEntity.ok(movieListService.getMovieListById(id, token));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<MovieListDTO> updateMovieListById(@PathVariable Long id, @RequestHeader(HttpHeaders.AUTHORIZATION) String token, @RequestBody MovieListDTO movieListDTO) {
        return ResponseEntity.ok(movieListService.updateMovieListById(id, token, movieListDTO));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<HttpStatus> deleteMovieListById(@PathVariable Long id, @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        movieListService.deleteMovieListById(id, token);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{movieListId}/movies/{movieId}")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<MovieListDTO> addMovieToListById(@PathVariable Long movieListId,
                                                         @PathVariable Long movieId,
                                                         @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        return ResponseEntity.ok(movieListService.addMovieToListById(movieListId, movieId, token));
    }

    @DeleteMapping("/{movieListId}/movies/{movieId}")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<MovieListDTO> deleteMovieFromListById(@PathVariable Long movieListId,
                                                              @PathVariable Long movieId,
                                                              @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {


        return ResponseEntity.ok(movieListService.deleteMovieFromListById(movieListId, movieId, token));
    }

    @GetMapping("/users/{userId}")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<List<MovieListDTO>> getAllMovieListsForUser(@PathVariable Long userId, @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        return ResponseEntity.ok(movieListService.getAllMovieListsForUser(userId, token));
    }
}
