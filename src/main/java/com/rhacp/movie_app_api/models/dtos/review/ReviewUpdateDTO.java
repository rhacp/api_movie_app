package com.rhacp.movie_app_api.models.dtos.review;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewUpdateDTO {

    @NotBlank
    private String reviewText;

    @NotNull
    @Min(0)
    @Max(10)
    private Integer rating;
}
