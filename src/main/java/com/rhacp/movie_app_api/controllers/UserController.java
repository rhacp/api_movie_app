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

    @PostMapping("/register")
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(userService.createUser(userDTO));
    }

    @GetMapping()
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<List<UserDTO>> getUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id,
                                               @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        return ResponseEntity.ok(userService.getUserById(id, token));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<Map<String, String>> deleteUserById(@PathVariable Long id,
                                                              @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        return ResponseEntity.ok(userService.deleteUserById(id, token));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<UserDTO> updateUserById(@PathVariable Long id,
                                                  @Valid @RequestBody UserUpdateDTO userDTO,
                                                  @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        return ResponseEntity.ok(userService.updateUserById(id, userDTO, token));
    }
}
