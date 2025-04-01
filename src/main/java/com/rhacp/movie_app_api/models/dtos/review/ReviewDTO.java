package com.rhacp.movie_app_api.models.dtos.review;

import com.rhacp.movie_app_api.models.entities.Movie;
import com.rhacp.movie_app_api.models.entities.user.User;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDTO {

    private Long id;

    @NotBlank
    private String reviewText;

    private LocalDate creationDate;

    private LocalTime creationTime;

    private User userReview;

    private Movie reviewMovie;
}
