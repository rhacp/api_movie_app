package com.rhacp.movie_app_api.services.user;

import com.rhacp.movie_app_api.exceptions.ResourceAlreadyExistsException;
import com.rhacp.movie_app_api.exceptions.ResourceNotFoundException;
import com.rhacp.movie_app_api.models.dtos.UserDTO;
import com.rhacp.movie_app_api.models.entities.user.User;
import com.rhacp.movie_app_api.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserServiceValidationImpl implements UserServiceValidation {

    private final UserRepository userRepository;

    public UserServiceValidationImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    @Override
    public void validateUserAlreadyExists(UserDTO userDTO) {
        User userFound = userRepository.findUserByEmail(userDTO.getEmail());

        if (userFound != null) {
            throw new ResourceAlreadyExistsException("A user with the email " + userDTO.getEmail() + " already exists");
        }
    }

    @Transactional
    @Override
    public User getValidUser(Long userId, String methodName) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User with id " + userId + " not found"));
        log.info("User with id {} retrieved. Method: {}", userId, methodName);

        return user;
    }
}
