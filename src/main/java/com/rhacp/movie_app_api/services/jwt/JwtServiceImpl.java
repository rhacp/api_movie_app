package com.rhacp.movie_app_api.services.jwt;

import com.rhacp.movie_app_api.exceptions.CustomSignatureMismatchException;
import com.rhacp.movie_app_api.exceptions.CustomExpiredTokenException;
import com.rhacp.movie_app_api.models.dtos.AuthRequestDTO;
import com.rhacp.movie_app_api.models.dtos.JwtDTO;
import com.rhacp.movie_app_api.utils.properties.Properties;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Slf4j
@Service
public class JwtServiceImpl implements JwtService {

    private final Properties properties;

    private final AuthenticationManager authenticationManager;

    public JwtServiceImpl(Properties properties, AuthenticationManager authenticationManager) {
        this.properties = properties;
        this.authenticationManager = authenticationManager;
    }

    // Generate token with given user name
    public JwtDTO generateToken(String userName) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userName);
    }

    // Create a JWT token with specified claims and subject (user name)
    private JwtDTO createToken(Map<String, Object> claims, String userName) {
        Date expiry = new Date(System.currentTimeMillis() + 1000 * 60 * 30);
        JwtBuilder jwtBuilder = Jwts.builder()
                .setClaims(claims)
                .setSubject(userName)
                .setIssuedAt(new Date())
                .setExpiration(expiry) // Token valid for 30 minutes
                .signWith(getSignKey(), SignatureAlgorithm.HS256);

        return new JwtDTO(jwtBuilder.compact(), new java.sql.Timestamp(expiry.getTime()).toLocalDateTime());
    }

    // Get the signing key for JWT token
    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(properties.getSecret());
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // Extract the username from the token
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // Extract the expiration date from the token
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // Extract a claim from the token
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // Extract all claims from the token
    private Claims extractAllClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(getSignKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (SignatureException e) {
            throw new CustomSignatureMismatchException("Invalid token.");
        } catch (ExpiredJwtException e) {
            throw new CustomExpiredTokenException("Token has expired.");
        }
    }

    // Check if the token is expired
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // Validate the token against user details and expiration
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public JwtDTO authenticateAndGetToken(AuthRequestDTO authRequestDTO) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequestDTO.getUsername(), authRequestDTO.getPassword())
        );

        return this.generateToken(authRequestDTO.getUsername());
    }
}
