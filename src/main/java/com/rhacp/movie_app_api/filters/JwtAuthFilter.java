package com.rhacp.movie_app_api.filters;

import com.rhacp.movie_app_api.exceptions.CustomExpiredTokenException;
import com.rhacp.movie_app_api.exceptions.CustomSignatureMismatchException;
import com.rhacp.movie_app_api.exceptions.ResourceNotFoundException;
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

@Slf4j
@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    private final UserServiceHelp userServiceHelp;

    private final HandlerExceptionResolver handlerExceptionResolver;

    public JwtAuthFilter(JwtService jwtService, UserServiceHelp userServiceHelp, HandlerExceptionResolver handlerExceptionResolver) {
        this.jwtService = jwtService;
        this.userServiceHelp = userServiceHelp;
        this.handlerExceptionResolver = handlerExceptionResolver;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
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

                // Validate token and set authentication
                if (jwtService.validateToken(token, userDetails)) {
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

            // Continue the filter chain
            filterChain.doFilter(request, response);
        } catch (CustomSignatureMismatchException | CustomExpiredTokenException | UsernameNotFoundException e) {
            handlerExceptionResolver.resolveException(request, response, null, e);
        } catch (MalformedJwtException e) {
            handlerExceptionResolver.resolveException(request, response, null, new CustomSignatureMismatchException("Malformed token."));
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        return path.equals("/api/v1/auth/generateToken") || path.equals("/api/v1/users/register");
    }
}
