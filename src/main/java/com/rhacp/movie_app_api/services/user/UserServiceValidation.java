package com.rhacp.movie_app_api.services.user;

import com.rhacp.movie_app_api.exceptions.ResourceAlreadyExistsException;
import com.rhacp.movie_app_api.exceptions.ResourceNotFoundException;
import com.rhacp.movie_app_api.models.dtos.user.UserDTO;
import com.rhacp.movie_app_api.models.dtos.user.UserInputDTO;
import com.rhacp.movie_app_api.models.entities.user.User;

public interface UserServiceValidation {

    /**
     * Checks if User already exists.
     *
     * @param userInputDTO UserDTO to check.
     * @throws ResourceAlreadyExistsException if User exists.
     */
    void validateUserAlreadyExists(UserInputDTO userInputDTO);

    /**
     * Search for User with the specified id and returns it.
     *
     * @param id Id to check for.
     * @param methodName Caller.
     * @return User.
     * @exception ResourceNotFoundException If User not found.
     */
    User getValidUser(Long id, String methodName);

    /**
     * Identify User by token.
     *
     * @param token Token to check for.
     * @param methodName Caller.
     * @return User.
     * @exception ResourceNotFoundException If user not found.
     */
    User getValidUserByToken(String token, String methodName);
}
