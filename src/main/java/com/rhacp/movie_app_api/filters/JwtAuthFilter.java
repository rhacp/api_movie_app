package com.rhacp.movie_app_api.filters;

import com.rhacp.movie_app_api.exceptions.CustomExpiredTokenException;
import com.rhacp.movie_app_api.exceptions.CustomSignatureMismatchException;
import com.rhacp.movie_app_api.services.jwt.JwtService;
import com.rhacp.movie_app_api.services.user.UserServiceHelp;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

/**
 * JWT authorization filter which executes once per request and checks the authorization token.
 */
@Slf4j
@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    private final UserServiceHelp userServiceHelp;

    private final HandlerExceptionResolver handlerExceptionResolver;

    public JwtAuthFilter(JwtService jwtService,
                         UserServiceHelp userServiceHelp,
                         HandlerExceptionResolver handlerExceptionResolver) {
        this.jwtService = jwtService;
        this.userServiceHelp = userServiceHelp;
        this.handlerExceptionResolver = handlerExceptionResolver;
    }

    /**
     * Jwt authentication filter.
     *
     * @param request HttpServletRequest.
     * @param response HttpServletResponse.
     * @param filterChain FilterChain used.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            String token = null;
            String username = null;
            // Retrieve the Authorization header
            String authHeader = request.getHeader("Authorization");

            if (authHeader == null) {
                throw new CustomSignatureMismatchException("Authorization header is missing");
            }

            // Check if the header starts with "Bearer "
            if (authHeader.startsWith("Bearer ")) {
                token = authHeader.substring(7); // Extract token
                username = jwtService.extractUsername(token); // Extract username from token
            } else {
                throw new CustomSignatureMismatchException("Authorization header not starting with \"Bearer \"");
            }

            // If the token is valid and no authentication is set in the context
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userServiceHelp.loadUserByUsername(username);

                extracted(request, token, userDetails);
            }

            // Continue the filter chain
            filterChain.doFilter(request, response);
        } catch (CustomSignatureMismatchException | CustomExpiredTokenException | UsernameNotFoundException e) {
            handlerExceptionResolver.resolveException(request, response, null, e);
        } catch (MalformedJwtException e) {
            handlerExceptionResolver.resolveException(request, response, null, new CustomSignatureMismatchException("Malformed token."));
        }
    }

    /**
     * Helper function to validate token and set authentication.
     *
     * @param request HttpServletRequest.
     * @param token User token.
     * @param userDetails Details of the user.
     */
    private void extracted(HttpServletRequest request,
                           String token,
                           UserDetails userDetails) {
        // Validate token and set authentication
        if (Boolean.TRUE.equals(jwtService.validateToken(token, userDetails))) {
            try {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * Helper function to validate token and set authentication.
     *
     * @param request HttpServletRequest.
     * @return Boolean value identifying paths that should not be filtered.
     */
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        return path.equals("/api/v1/auth/generateToken")
                || path.startsWith("/swagger-ui")
                || path.startsWith("/v3/api-docs")
                || path.equals("/v3/api-docs.yaml")
                || path.startsWith("/api/v1/searchIndex")
                || path.startsWith("/api/v1/movie");
    }
}