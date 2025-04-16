package com.rhacp.movie_app_api.models.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rhacp.movie_app_api.models.dtos.movie_list.MovieListDTO;
import com.rhacp.movie_app_api.models.dtos.review.ReviewDTO;
import com.rhacp.movie_app_api.models.entities.SearchIndex;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovieDTO {

    private Long id;

    private String title;

    private String overview;

    private String posterPath;

    private Integer rating;

    private Long movieId;

    @JsonIgnore
    private SearchIndex searchIndex;

    private List<MovieListDTO> movieLists;

    private List<ReviewDTO> reviewList = new ArrayList<>();
}
