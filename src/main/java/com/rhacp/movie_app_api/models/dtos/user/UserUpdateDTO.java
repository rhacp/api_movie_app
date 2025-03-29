package com.rhacp.movie_app_api.models.dtos.user;

import com.rhacp.movie_app_api.models.entities.Review;
import com.rhacp.movie_app_api.utils.validators.RolePattern;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateDTO {

    private Long id;

    @Size(min = 3, max = 30, message = "Must be between 3 and 30 characters.")
    private String name;

    @Email
    @Size(min = 3, max = 30, message = "Must be between 3 and 30 characters.")
    private String email;

    @Size(min = 3, max = 30, message = "Must be between 3 and 30 characters.")
    private String password;

    @RolePattern(anyOf = {"role_user", "role_admin", "role_user,role_admin"})
    private String roles;

    //    @JsonIgnore
    private List<Review> reviewList = new ArrayList<>();
}
