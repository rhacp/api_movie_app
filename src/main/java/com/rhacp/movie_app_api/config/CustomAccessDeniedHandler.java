package com.rhacp.movie_app_api.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    /**
     * Custom handler exception for AccessDeniedException.
     *
     * @param request HttpServletRequest.
     * @param response HttpServletResponse.
     * @param accessDeniedException Exception.
     */
    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setStatus(403);
        response.setContentType("application/json");

        String responseMessage = "{ \"message\": \"User not allowed here.\" }";

        PrintWriter out = response.getWriter();
        out.print(responseMessage);
        out.flush();
    }
}
