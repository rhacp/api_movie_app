package com.rhacp.movie_app_api.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rhacp.movie_app_api.exceptions.CustomSignatureMismatch;
import com.rhacp.movie_app_api.exceptions.GlobalHandlerExceptions;
import com.rhacp.movie_app_api.services.jwt.JwtService;
import com.rhacp.movie_app_api.services.user.UserService;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.ErrorResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    private final UserService userService;

    private final GlobalHandlerExceptions globalHandlerExceptions;

    public JwtAuthFilter(JwtService jwtService, UserService userService, GlobalHandlerExceptions globalHandlerExceptions) {
        this.jwtService = jwtService;
        this.userService = userService;
        this.globalHandlerExceptions = globalHandlerExceptions;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String token = null;
            String username = null;
            // Retrieve the Authorization header
            String authHeader = request.getHeader("Authorization");

            if (authHeader == null) {
                throw new CustomSignatureMismatch("Authorization header is missing");
            }


            // Check if the header starts with "Bearer "
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                token = authHeader.substring(7); // Extract token
                username = jwtService.extractUsername(token); // Extract username from token
            }

            // If the token is valid and no authentication is set in the context
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userService.loadUserByUsername(username);

                // Validate token and set authentication
                if (jwtService.validateToken(token, userDetails)) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }

            // Continue the filter chain
            filterChain.doFilter(request, response);
        } catch (RuntimeException e) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            log.error(e.getClass().getCanonicalName() + ": " + e.getMessage());

//            Map<String, String> mapResponse = new HashMap<>();
//            mapResponse.put("message", e.getMessage());

            response.setContentType("application/json");
            response.getWriter().write("{\n\"message\": \"Authentication header is missing or incorrect.\"\n}");
        }
    }
}
