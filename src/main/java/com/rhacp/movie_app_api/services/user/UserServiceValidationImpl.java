package com.rhacp.movie_app_api.services.user;

import com.rhacp.movie_app_api.exceptions.ResourceAlreadyExistsException;
import com.rhacp.movie_app_api.exceptions.ResourceNotFoundException;
import com.rhacp.movie_app_api.models.dtos.UserDTO;
import com.rhacp.movie_app_api.models.entities.user.User;
import com.rhacp.movie_app_api.repositories.UserRepository;
import com.rhacp.movie_app_api.services.jwt.JwtService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserServiceValidationImpl implements UserServiceValidation {

    private final UserRepository userRepository;

    private final JwtService jwtService;

    public UserServiceValidationImpl(UserRepository userRepository, JwtService jwtService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    @Transactional
    @Override
    public void validateUserAlreadyExists(UserDTO userDTO) {
        User userFound = userRepository.findUserByEmail(userDTO.getEmail());

        if (userFound != null) {
            throw new ResourceAlreadyExistsException("A user with the email " + userDTO.getEmail() + " already exists");
        }
    }

    @Override
    public User getValidUser(Long userId, String methodName) {
        User userFound = userRepository.findUserById(userId);
        if (userFound == null) {
            throw new ResourceNotFoundException("User with id " + userId + " not found");
        }

        log.info("User with id {} retrieved. Method: {}", userId, methodName);

        return userFound;
    }

    @Transactional
    @Override
    public User getValidUserByToken(String token, String methodName) {
        String email = jwtService.extractUsername(token); // Extract username from token

        User userFound = userRepository.findUserByEmail(email);
        if (userFound == null) {
            throw new ResourceNotFoundException("User with email " + email + " not found");
        }

        log.info("User with email {} retrieved. Method: {}", email, methodName);

        return userFound;
    }
}
