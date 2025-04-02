package com.rhacp.movie_app_api.services.jwt;

import com.rhacp.movie_app_api.exceptions.CustomSignatureMismatchException;
import com.rhacp.movie_app_api.exceptions.CustomExpiredTokenException;
import com.rhacp.movie_app_api.models.dtos.AuthRequestDTO;
import com.rhacp.movie_app_api.models.dtos.JwtDTO;
import com.rhacp.movie_app_api.utils.properties.Properties;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.DecodingException;
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

    public JwtServiceImpl(Properties properties,
                          AuthenticationManager authenticationManager) {
        this.properties = properties;
        this.authenticationManager = authenticationManager;
    }

    public JwtDTO generateToken(String userName) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userName);
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token,
                              Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public Boolean validateToken(String token,
                                 UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));

    }

    public JwtDTO authenticateAndGetToken(AuthRequestDTO authRequestDTO) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequestDTO.getUsername(), authRequestDTO.getPassword())
        );
        log.info("Token retrieved for user {}. Method: authenticateAndGetToken", authRequestDTO.getUsername());

        return this.generateToken(authRequestDTO.getUsername());
    }

    public String getAllClaims(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Create a JWT token with specified claims and subject (username).
     *
     * @param claims Map<String, Object>.
     * @return JwtDTO : token.
     */
    private JwtDTO createToken(Map<String, Object> claims,
                               String userName) {
        Date expiry = new Date(System.currentTimeMillis() + 1000 * 60 * properties.getTokenLifetime());
        System.out.println(expiry);
        JwtBuilder jwtBuilder = Jwts.builder()
                .setClaims(claims)
                .setSubject(userName)
                .setIssuedAt(new Date())
                .setExpiration(expiry) // Token valid for 30 minutes
                .signWith(getSignKey(), SignatureAlgorithm.HS256);

        return new JwtDTO(jwtBuilder.compact(), new java.sql.Timestamp(expiry.getTime()).toLocalDateTime());
    }

    /**
     * Get the signing key for JWT token.
     *
     * @return Key.
     */
    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(properties.getSecret());
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * Extract all claims from the token.
     *
     * @param token User token.
     * @return Claim.
     */
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
        } catch (DecodingException e) {
            throw new CustomExpiredTokenException("Decoding failed.");
        }
    }

    /**
     * Check if the token is expired.
     *
     * @param token User token.
     * @return Boolean.
     */
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
}
