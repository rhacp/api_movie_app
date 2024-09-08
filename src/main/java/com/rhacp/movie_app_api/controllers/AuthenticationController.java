package com.rhacp.movie_app_api.controllers;

import com.rhacp.movie_app_api.models.dtos.AuthRequestDTO;
import com.rhacp.movie_app_api.models.dtos.JwtDTO;
import com.rhacp.movie_app_api.services.jwt.JwtService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    private final JwtService jwtService;

    public AuthenticationController(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @GetMapping("/welcome")
    public String welcome() {
        return "Welcome this endpoint is not secure";
    }

    @PostMapping("/generateToken")
    public ResponseEntity<JwtDTO> authenticateAndGetToken(@RequestBody AuthRequestDTO authRequestDTO) {
        return ResponseEntity.ok(jwtService.authenticateAndGetToken(authRequestDTO));
    }
}
