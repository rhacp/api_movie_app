package com.rhacp.movie_app_api.models.dtos.movie_api_response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class MovieAPIResponseDTO {

    private Integer page;

    private List<MovieAPIDTO> results = new ArrayList<>();

    @JsonProperty("total_pages")
    private Integer totalPages;

    @JsonProperty("total_results")
    private Integer totalResults;
}
