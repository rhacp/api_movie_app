package com.rhacp.movie_app_api.models.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.rhacp.movie_app_api.models.entities.SearchIndex;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovieDTO {

    private Long id;

    private String title;

    private String overview;

    @JsonProperty("poster_path")
    private String posterPath;

    private SearchIndex searchIndex;
}
