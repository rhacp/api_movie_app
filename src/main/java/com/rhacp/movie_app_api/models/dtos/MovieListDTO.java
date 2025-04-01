package com.rhacp.movie_app_api.models.dtos;

import com.rhacp.movie_app_api.models.entities.Movie;
import com.rhacp.movie_app_api.models.entities.user.User;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovieListDTO {

    private Long id;

    @NotBlank
    private String name;

    private LocalDate date;

    private LocalTime time;

    @NotBlank
    private String description;

    private List<Movie> movies = new ArrayList<>();

    private User userMovie;
}
