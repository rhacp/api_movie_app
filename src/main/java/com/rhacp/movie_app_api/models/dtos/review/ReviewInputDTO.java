package com.rhacp.movie_app_api.models.dtos.review;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewInputDTO {

    @NotBlank
    private String reviewText;

    @NotNull
    private Long movieId;
}