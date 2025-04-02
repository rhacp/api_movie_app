package com.rhacp.movie_app_api.services.jwt;

import com.rhacp.movie_app_api.models.dtos.AuthRequestDTO;
import com.rhacp.movie_app_api.models.dtos.JwtDTO;
import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;
import java.util.function.Function;

public interface JwtService {

    /**
     * Generate token with given username.
     *
     * @param userName
     * @return JwtDTO
     */
    JwtDTO generateToken(String userName);

    /**
     * Extract the username from the token.
     *
     * @param token User token.
     * @return Username.
     */
    String extractUsername(String token);

    /**
     * Extract the expiration date from the token.
     *
     * @param token User token.
     * @return Expiration date.
     */
    Date extractExpiration(String token);

    /**
     * Extract a claim from the token.
     *
     * @param token User token.
     * @return Expiration date.
     */
    <T> T extractClaim(String token,
                       Function<Claims, T> claimsResolver);

    /**
     * Validate the token against user details and expiration.
     *
     * @param token User token.
     * @return Boolean : if token is valid.
     */
    Boolean validateToken(String token,
                          UserDetails userDetails);

    /**
     * Check credentials and get token.
     *
     * @param authRequestDTO Credentials.
     * @return JwtDTO containing the token.
     */
    JwtDTO authenticateAndGetToken(AuthRequestDTO authRequestDTO);
}
