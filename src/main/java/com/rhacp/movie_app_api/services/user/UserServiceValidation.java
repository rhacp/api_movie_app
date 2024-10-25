package com.rhacp.movie_app_api.services.user;

import com.rhacp.movie_app_api.models.dtos.UserDTO;
import com.rhacp.movie_app_api.models.entities.user.User;

public interface UserServiceValidation {

    /**
     * Validates if a user with the same id as the given userDTO exists.
     * @param userDTO userDTO to get the id from
     * @exception ResourceAlreadyExists if user exists
     */
    void validateUserAlreadyExists(UserDTO userDTO);

    /**
     * Search for a user with the specified id and returns it.
     * @param email Email to check for.
     * @param methodName methodName.
     * @return User found user.
     * @exception ResourceNotFoundException If user not found.
     */
    User getValidUser(String email, String methodName);
}
