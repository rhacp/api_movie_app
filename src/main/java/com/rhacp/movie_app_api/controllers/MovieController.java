package com.rhacp.movie_app_api.controllers;

import com.rhacp.movie_app_api.models.dtos.MovieDTO;
import com.rhacp.movie_app_api.services.movie.MovieService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/movie")
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    /**
     * GET endpoint to retrieve movie by title.
     *
     * @param movieTitle Title of the movie.
     * @return ResponseEntity.ok : MovieDTO retrieved.
     */
    @GetMapping
    public ResponseEntity<MovieDTO> getMovieByTitle(@RequestParam String movieTitle) {
        return ResponseEntity.ok(movieService.getMovieByTitle(movieTitle));
    }
}
