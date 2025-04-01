package com.rhacp.movie_app_api.controllers;

import com.rhacp.movie_app_api.models.dtos.AuthRequestDTO;
import com.rhacp.movie_app_api.models.dtos.JwtDTO;
import com.rhacp.movie_app_api.services.jwt.JwtService;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    private final JwtService jwtService;

    public AuthenticationController(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    /**
     * POST endpoint to get token based on account details.
     *
     * @param authRequestDTO DTO object containing username and password.
     * @return ResponseEntity.ok : JwtDTO containing the token.
     */
    @PostMapping("/generateToken")
    public ResponseEntity<JwtDTO> authenticateAndGetToken(@Valid @RequestBody AuthRequestDTO authRequestDTO) {
        return ResponseEntity.ok(jwtService.authenticateAndGetToken(authRequestDTO));
    }
}
