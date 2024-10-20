package com.rhacp.movie_app_api.services.user;

import com.rhacp.movie_app_api.models.dtos.UserDTO;

import java.util.List;

public interface UserService {

    /**
     * Creates a user based on the given userDTO.
     *
     * @param userDTO Given userDTO.
     * @return UserDTO of the saved user.
     */
    UserDTO createUser(UserDTO userDTO);

    /**
     * Returns the list of all existing users.
     *
     * @return List of UserDTO.
     */
    List<UserDTO> getAllUsers();

    /**
     * Return a user based on id.
     *
     * @param userId User id to search for.
     * @return UserDTO returned user DTO.
     */
    UserDTO getUserById(Long userId);

    /**
     * Delete user by the given id.
     *
     * @param userId User id to delete.
     * @return String message.
     */
    String deleteUserById(Long userId);
}
