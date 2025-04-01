package com.rhacp.movie_app_api.controllers;

import com.rhacp.movie_app_api.models.dtos.MovieDTO;
import com.rhacp.movie_app_api.services.search_index.SearchIndexService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/searchIndex")
public class SearchIndexController {

    private final SearchIndexService searchIndexService;

    public SearchIndexController(SearchIndexService searchIndexService) {
        this.searchIndexService = searchIndexService;
    }

    /**
     * GET endpoint to retrieve the default <code>SearchIndex</code> movie list.
     *
     * @param keyword Search keyword for the resulted list.
     * @return <code>ResponseEntity.ok</code> : List of <code>MovieDTO</code>.
     */
    @GetMapping
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<List<MovieDTO>> getMovieList(@RequestParam(required = false) String keyword) {
        return ResponseEntity.ok(searchIndexService.getMovieList(keyword));
    }
}
