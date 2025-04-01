package com.rhacp.movie_app_api.services.user;

import com.rhacp.movie_app_api.exceptions.CustomForbiddenResourceException;
import com.rhacp.movie_app_api.models.dtos.user.UserDTO;
import com.rhacp.movie_app_api.models.dtos.user.UserUpdateDTO;
import com.rhacp.movie_app_api.models.entities.user.User;
import com.rhacp.movie_app_api.repositories.UserRepository;
import com.rhacp.movie_app_api.utils.enums.Role;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final ModelMapper modelMapper;

    private final UserServiceValidation userServiceValidation;

    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository,
                           ModelMapper modelMapper,
                           UserServiceValidation userServiceValidation,
                           PasswordEncoder passwordEncoder) {
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
        user.setCreationDate(LocalDate.now());
        user.setCreationTime(LocalTime.now().withNano(0));

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
    public UserDTO getUserById(Long id,
                               String token) {
        User userFoundFromToken = userServiceValidation.getValidUserByToken(token, "getUserById");
        User userFoundById = userServiceValidation.getValidUser(id, "getUserById");

        checkIfUserTheSame(userFoundFromToken, userFoundById);

        return modelMapper.map(userFoundById, UserDTO.class);
    }

    //check if user admin or user the same
    @Transactional
    @Override
    public Map<String, String> deleteUserById(Long id,
                                              String token) {
        User foundUser = userServiceValidation.getValidUser(id, "deleteUserById");
        checkIfUserTheSame(getUserByToken(token), foundUser);

        userRepository.deleteById(id);
        log.info("User {} deleted. Method {}.", id, "deleteUserById");

        Map<String, String> response = new HashMap<>();
        response.put("message", "User with id " + id + " deleted.");

        return response;
    }

    @Transactional
    @Override
    public UserDTO updateUserById(Long id,
                                  UserUpdateDTO userDTO,
                                  String token) {
        User userFound = userServiceValidation.getValidUser(id, "updateUser");

        checkIfUserTheSame(getUserByToken(token), userFound);

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
    public void checkIfUserTheSame(User userFoundFromToken,
                                   User userFoundById) {
        //If user not ROLE_ADMIN and username from token not the same as username from id, then forbidden resource.
        if (!userFoundFromToken.getEmail().equals(userFoundById.getEmail())
                && !userFoundFromToken.getRole().getRoleLabel().equalsIgnoreCase("role_admin")) {
            throw new CustomForbiddenResourceException("User not allowed here.");
        }
    }

    @Transactional
    @Override
    public User getUserEntityById(Long id) {
        return userServiceValidation.getValidUser(id, "getUserEntityById");
    }

    /**
     * Helper method that updates the given user from the given DTO (only the existing fields).
     * @param user Existing user.
     * @param userDTO Update DTO.
     */
    private void updateUserFromDTO(User user,
                                   UserUpdateDTO userDTO) {
        if (userDTO.getName() != null) {
            user.setName(userDTO.getName());
        }

        if (userDTO.getEmail() != null) {
            user.setEmail(userDTO.getEmail());
        }

        if (userDTO.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        }

        if (userDTO.getRole() != null) {
            updateUserRole(user, userDTO.getRole());
        }
    }

    private void updateUserRole(User user,
                                String roleToSet) {
        if (roleToSet.equalsIgnoreCase("role_admin")) {
            user.setRole(Role.ROLE_ADMIN);
        }

        if (roleToSet.equalsIgnoreCase("role_user")) {
            user.setRole(Role.ROLE_USER);
        }
    }
}
