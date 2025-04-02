package com.rhacp.movie_app_api.services.user;

import com.rhacp.movie_app_api.exceptions.ResourceAlreadyExistsException;
import com.rhacp.movie_app_api.exceptions.ResourceNotFoundException;
import com.rhacp.movie_app_api.models.dtos.user.UserInputDTO;
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

    public UserServiceValidationImpl(UserRepository userRepository,
                                     JwtService jwtService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    @Transactional
    @Override
    public void validateUserAlreadyExists(UserInputDTO userInputDTO) {
        User userFound = userRepository.findUserByEmail(userInputDTO.getEmail());

        if (userFound != null) {
            throw new ResourceAlreadyExistsException("A user with the email " + userInputDTO.getEmail() + " already exists.");
        }
    }

    @Transactional
    @Override
    public User getValidUser(Long id,
                             String methodName) {
        User userFound = userRepository.findUserById(id);
        if (userFound == null) {
            throw new ResourceNotFoundException("User with id " + id + " not found.");
        }

        log.info("User with id {} retrieved. Method: {}", id, methodName);

        return userFound;
    }

    @Transactional
    @Override
    public User getValidUserByToken(String token,
                                    String methodName) {
        String actualToken = token.substring(6);
        String email = jwtService.extractUsername(actualToken); // Extract username from token

        User userFound = userRepository.findUserByEmail(email);
        if (userFound == null) {
            throw new ResourceNotFoundException("User with email " + email + " not found from given token.");
        }

        log.info("User with email {} retrieved from given token. Method: {}", email, methodName);

        return userFound;
    }
}
