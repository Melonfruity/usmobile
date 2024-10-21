package com.usmobile.assessment.user_service.security.util;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        // Extract JWT from the Authorization header
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);

            // Validate the JWT token
            if (JwtUtil.validateToken(token)) {
                // Extract userId from the JWT token
                String userId = JwtUtil.extractUserId(token);

                // I only care about if the UserId exists in the JWT
                if (userId != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    // Create a UsernamePasswordAuthenticationToken with the userId and set it in the SecurityContext
                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(userId, null, null);

                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    // Set the authentication in the SecurityContext
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
        }

        // Continue the filter chain
        filterChain.doFilter(request, response);
    }
}
