package com.rhacp.movie_app_api.services.user;

import com.rhacp.movie_app_api.models.dtos.user.UserDTO;
import com.rhacp.movie_app_api.models.dtos.user.UserUpdateDTO;
import com.rhacp.movie_app_api.models.entities.user.User;

import java.util.List;
import java.util.Map;

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
    UserDTO getUserById(Long userId, String token);

    /**
     * Delete user by the given id.
     *
     * @param userId User id to delete.
     * @return String message.
     */
    Map<String, String> deleteUserById(Long userId, String token);

    UserDTO updateUserById(Long userId, UserUpdateDTO userDTO, String token);

    User getUserByToken(String token);

    void checkIfUserTheSame(User userFoundFromToken, User userFoundById);

    User getUserEntityById(Long id);
}
