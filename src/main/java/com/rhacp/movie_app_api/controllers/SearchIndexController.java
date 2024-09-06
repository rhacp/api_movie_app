package com.rhacp.movie_app_api.controllers;

import com.rhacp.movie_app_api.models.dtos.MovieDTO;
import com.rhacp.movie_app_api.services.search_index.SearchIndexService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/searchindex")
public class SearchIndexController {

    private final SearchIndexService searchIndexService;

    public SearchIndexController(SearchIndexService searchIndexService) {
        this.searchIndexService = searchIndexService;
    }

    @GetMapping
    public ResponseEntity<List<MovieDTO>> searchIndex() {
        return ResponseEntity.ok(searchIndexService.getMovieList());
    }
}
