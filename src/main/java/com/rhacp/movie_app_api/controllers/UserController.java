package com.rhacp.movie_app_api.controllers;

import com.rhacp.movie_app_api.models.dtos.user.UserDTO;
import com.rhacp.movie_app_api.models.dtos.user.UserUpdateDTO;
import com.rhacp.movie_app_api.services.user.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * POST endpoint to create User.
     *
     * @param userDTO Received User DTO.
     * @return ResponseEntity.ok : UserDTO created.
     */
    @PostMapping("/register")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(userService.createUser(userDTO));
    }

    /**
     * GET endpoint to receive all Users.
     *
     * @return ResponseEntity.ok : List of UserDTO.
     */
    @GetMapping()
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    /**
     * GET endpoint to receive User by id.
     *
     * @param id Id of the User to be returned.
     * @return ResponseEntity.ok : UserDTO.
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id,
                                               @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        return ResponseEntity.ok(userService.getUserById(id, token));
    }

    /**
     * DELETE endpoint to remove User by id.
     *
     * @param id Id of the User to be deleted.
     * @param token User token.
     * @return ResponseEntity.ok : Map of String and string, confirmation message.
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<Map<String, String>> deleteUserById(@PathVariable Long id,
                                                              @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        return ResponseEntity.ok(userService.deleteUserById(id, token));
    }

    /**
     * PUT endpoint to update User by id.
     *
     * @param id Id of the MovieList to be updated.
     * @param userDTO DTO containing the new info.
     * @param token User token.
     * @return ResponseEntity.ok : updated UserDTO.
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<UserDTO> updateUserById(@PathVariable Long id,
                                                  @Valid @RequestBody UserUpdateDTO userDTO,
                                                  @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        return ResponseEntity.ok(userService.updateUserById(id, userDTO, token));
    }
}
