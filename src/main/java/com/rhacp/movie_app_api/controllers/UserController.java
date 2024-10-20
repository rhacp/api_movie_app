package com.rhacp.movie_app_api.controllers;

import com.rhacp.movie_app_api.models.dtos.UserDTO;
import com.rhacp.movie_app_api.services.user.UserService;
import com.rhacp.movie_app_api.services.user.UserServiceHelp;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserServiceHelp userServiceHelp;

    private final UserService userService;

    public UserController(UserServiceHelp userServiceHelp, UserService userService) {
        this.userServiceHelp = userServiceHelp;
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(userService.createUser(userDTO));
    }

    @GetMapping("/user/userProfile")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public String userProfile() {
        return "Welcome to User Profile";
    }

    @GetMapping("/admin/adminProfile")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String adminProfile() {
        return "Welcome to Admin Profile";
    }
}
