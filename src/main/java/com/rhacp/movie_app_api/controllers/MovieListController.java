package com.rhacp.movie_app_api.controllers;

import com.rhacp.movie_app_api.models.dtos.MovieListDTO;
import com.rhacp.movie_app_api.services.movie_list.MovieListService;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/movieList")
public class MovieListController {

    private final MovieListService movieListService;

    public MovieListController(MovieListService movieListService) {
        this.movieListService = movieListService;
    }

    /**
     * POST endpoint to create MovieList.
     *
     * @param movieListDTO Received movieList DTO.
     * @return ResponseEntity.ok : MovieListDTO created.
     */
    @PostMapping
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<MovieListDTO> createMovieList(@Valid @RequestBody MovieListDTO movieListDTO,
                                                        @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        return ResponseEntity.ok(movieListService.createMovieList(movieListDTO, token));
    }

    /**
     * GET endpoint to receive all MovieList.
     *
     * @return ResponseEntity.ok : List of MovieListDTO.
     */
    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<List<MovieListDTO>> getAllMovieLists() {
        return ResponseEntity.ok(movieListService.getAllMovieLists());
    }

    /**
     * GET endpoint to receive MovieList by id.
     *
     * @param id Id of the MovieList to be returned.
     * @return ResponseEntity.ok : MovieListDTO.
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<MovieListDTO> getMovieListById(@PathVariable("id") Long id,
                                                         @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        return ResponseEntity.ok(movieListService.getMovieListById(id, token));
    }

    /**
     * PUT endpoint to update MovieList by id.
     *
     * @param id Id of the MovieList to be updated.
     * @param movieListDTO DTO containing the new info.
     * @param token User token.
     * @return ResponseEntity.ok : updated MovieListDTO.
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<MovieListDTO> updateMovieListById(@PathVariable Long id,
                                                            @RequestBody MovieListDTO movieListDTO,
                                                            @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        return ResponseEntity.ok(movieListService.updateMovieListById(id, movieListDTO, token));
    }

    /**
     * DELETE endpoint to remove MovieList by id.
     *
     * @param id Id of the MovieList to be deleted.
     * @param token User token.
     * @return ResponseEntity.ok : Map of String and string, confirmation message.
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<Map<String, String>> deleteMovieListById(@PathVariable Long id,
                                                                   @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        return ResponseEntity.ok(movieListService.deleteMovieListById(id, token));
    }

    /**
     * PUT endpoint to add Movie to MovieList.
     *
     * @param movieListId Id of the MovieList.
     * @param movieId Id of the Movie.
     * @param token User token.
     * @return ResponseEntity.ok : updated MovieListDTO.
     */
    @PutMapping("/{movieListId}/movies/{movieId}")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<MovieListDTO> addMovieToListById(@PathVariable Long movieListId,
                                                         @PathVariable Long movieId,
                                                         @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        return ResponseEntity.ok(movieListService.addMovieToListById(movieListId, movieId, token));
    }

    /**
     * DELETE endpoint to remove a Movie from a MovieList.
     *
     * @param movieListId Id of the MovieList.
     * @param movieId Id of the Movie.
     * @param token User token.
     * @return ResponseEntity.ok : updated MovieListDTO.
     */
    @DeleteMapping("/{movieListId}/movies/{movieId}")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<MovieListDTO> deleteMovieFromListById(@PathVariable Long movieListId,
                                                              @PathVariable Long movieId,
                                                              @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {


        return ResponseEntity.ok(movieListService.deleteMovieFromListById(movieListId, movieId, token));
    }

    /**
     * GET endpoint to receive all MovieLists for specific User.
     *
     * @param userId Id of the User.
     * @param token User token.
     * @return ResponseEntity.ok : List of MovieListDTO.
     */
    @GetMapping("/users/{userId}")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<List<MovieListDTO>> getAllMovieListsForUser(@PathVariable Long userId,
                                                                      @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        return ResponseEntity.ok(movieListService.getAllMovieListsForUser(userId, token));
    }
}
