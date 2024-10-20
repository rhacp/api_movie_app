package com.rhacp.movie_app_api.models.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rhacp.movie_app_api.models.entities.Review;
import com.rhacp.movie_app_api.utils.validators.RolePattern;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private Long id;

    @NotBlank
    @Size(min = 3, max = 30, message = "Must be between 3 and 30 characters.")
    private String name;

    @NotBlank
    @Size(min = 3, max = 30, message = "Must be between 3 and 30 characters.")
    private String email;

    @NotBlank
    @Size(min = 3, max = 30, message = "Must be between 3 and 30 characters.")
    private String password;

    @RolePattern(anyOf = {"role_user", "role_admin", "role_user,role_admin"})
    private String roles;

//    @JsonIgnore
    private List<Review> reviewList = new ArrayList<>();
}
