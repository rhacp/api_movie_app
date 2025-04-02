package com.rhacp.movie_app_api.models.dtos.movie_list;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovieListInputDTO {

    @NotBlank
    private String name;

    @NotBlank
    private String description;
}
