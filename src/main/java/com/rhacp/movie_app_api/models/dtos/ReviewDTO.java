package com.rhacp.movie_app_api.models.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDTO {

    private Long id;

    @NotNull
    private Long movieId;

    @NotBlank
    private String requestorUser;

    @NotBlank
    private String reviewText;

    private LocalDateTime dateTime;
}
