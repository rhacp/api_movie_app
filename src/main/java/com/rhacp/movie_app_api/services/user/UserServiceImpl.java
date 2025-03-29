package com.rhacp.movie_app_api.services.user;

import com.rhacp.movie_app_api.exceptions.CustomForbiddenResourceException;
import com.rhacp.movie_app_api.models.dtos.user.UserDTO;
import com.rhacp.movie_app_api.models.dtos.user.UserUpdateDTO;
import com.rhacp.movie_app_api.models.entities.user.User;
import com.rhacp.movie_app_api.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final ModelMapper modelMapper;

    private final UserServiceValidation userServiceValidation;

    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, UserServiceValidation userServiceValidation, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.userServiceValidation = userServiceValidation;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    @Override
    public UserDTO createUser(UserDTO userDTO) {
        userServiceValidation.validateUserAlreadyExists(userDTO);

        User user = modelMapper.map(userDTO, User.class);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userRepository.save(user);
        log.info("User {} inserted in db. Method: {}.", savedUser.getEmail(), "createUser");

        return modelMapper.map(savedUser, UserDTO.class);
    }

    @Transactional
    @Override
    public List<UserDTO> getAllUsers() {
        List<User> userList = userRepository.findAll();
        log.info("User list retrieved from db. Method: {}.", "getAllUsers");

        return userList.stream()
                .map(user -> modelMapper.map(user, UserDTO.class))
                .toList();
    }

    @Override
    public UserDTO getUserById(Long userId, String token) {
        User userFoundFromToken = userServiceValidation.getValidUserByToken(token, "getUserById");
        User userFoundById = userServiceValidation.getValidUser(userId, "getUserById");

        checkIfUserTheSame(userFoundFromToken, userFoundById);

        return modelMapper.map(userFoundById, UserDTO.class);
    }

    @Transactional
    @Override
    public String deleteUserById(Long userId) {
        userServiceValidation.getValidUser(userId, "deleteUserById");

        userRepository.deleteById(userId);
        log.info("User {} deleted. Method {}.", userId, "deleteUserById");

        return "User with id " + userId + " deleted.";
    }

    //check if users the same or admin !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    @Transactional
    @Override
    public UserDTO updateUserById(Long userId, UserUpdateDTO userDTO) {
        User userFound = userServiceValidation.getValidUser(userId, "updateUser");

        updateUserFromDTO(userFound, userDTO);
        User savedUser = userRepository.save(userFound);
        log.info("User {} : {} updated. Method: {}.", savedUser.getId(), savedUser.getEmail(), "updateUser");

        return modelMapper.map(savedUser, UserDTO.class);
    }

    @Override
    public User getUserByToken(String token) {
        return userServiceValidation.getValidUserByToken(token, "getUserByToken");
    }

    @Override
    public void checkIfUserTheSame(User userFoundFromToken, User userFoundById) {
        //If user not ROLE_ADMIN and username from token not the same as username from id, then forbidden resource.
        if (!userFoundFromToken.getEmail().equals(userFoundById.getEmail())
                && !userFoundFromToken.getRoles().equalsIgnoreCase("role_admin")) {
            throw new CustomForbiddenResourceException("User not allowed here.");
        }
    }

    /**
     * Helper method that updates the given user from the given DTO (only the existing fields).
     * @param user Existing user.
     * @param userDTO Update DTO.
     */
    private void updateUserFromDTO(User user, UserUpdateDTO userDTO) {
        if (userDTO.getName() != null) {
            user.setName(userDTO.getName());
        }

        if (userDTO.getEmail() != null) {
            user.setEmail(userDTO.getEmail());
        }

        if (userDTO.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        }

        if (userDTO.getRoles() != null) {
            user.setRoles(userDTO.getRoles());
        }
    }
}
