package com.rhacp.movie_app_api.models.dtos.movie_api_response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class MovieAPIDTO {

    private Boolean adult;

    @JsonProperty("backdrop_path")
    private String backdropPath;

    @JsonProperty("genre_ids")
    private List<Integer> genreIds = new ArrayList<>();

    @JsonProperty("id")
    private Long movieId;

    @JsonProperty("original_language")
    private String originalLanguage;

    private String overview;

    private Float popularity;

    @JsonProperty("poster_path")
    private String posterPath;

    @JsonProperty("release_date")
    private String releaseDate;

    private String title;

    private Boolean video;

    @JsonProperty("vote_average")
    private Float voteAverage;

    @JsonProperty("vote_count")
    private Integer voteCount;
}
