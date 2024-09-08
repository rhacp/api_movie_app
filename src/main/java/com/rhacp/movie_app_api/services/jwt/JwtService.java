package com.rhacp.movie_app_api.services.jwt;

import com.rhacp.movie_app_api.models.dtos.AuthRequestDTO;
import com.rhacp.movie_app_api.models.dtos.JwtDTO;
import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;
import java.util.function.Function;

public interface JwtService {

    // Generate token with given user name
    JwtDTO generateToken(String userName);

    // Extract the username from the token
    String extractUsername(String token);

    // Extract the expiration date from the token
    Date extractExpiration(String token);

    // Extract a claim from the token
    <T> T extractClaim(String token, Function<Claims, T> claimsResolver);

    // Validate the token against user details and expiration
    Boolean validateToken(String token, UserDetails userDetails);

    public JwtDTO authenticateAndGetToken(AuthRequestDTO authRequestDTO);
}
