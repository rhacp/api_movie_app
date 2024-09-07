package com.rhacp.movie_app_api.controllers;

import com.rhacp.movie_app_api.models.dtos.MovieDTO;
import com.rhacp.movie_app_api.services.search_index.SearchIndexService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/searchindex")
public class SearchIndexController {

    private final SearchIndexService searchIndexService;

    public SearchIndexController(SearchIndexService searchIndexService) {
        this.searchIndexService = searchIndexService;
    }

    /**
     * GET endpoint to retrieve the default movie list.
     *
     * @return ResponseEntity.ok : MovieDTO list.
     */
    @GetMapping
    public ResponseEntity<List<MovieDTO>> getMovieList(@RequestParam(required = false) String keyword) {
        return ResponseEntity.ok(searchIndexService.getMovieList(keyword));
    }
}
