package com.example.demo.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    private final JwtService jwtService;

    public JwtAuthenticationFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        try {
            String token = extractToken(request);

            if (token != null && isTokenValid(token)) {
                Long userId = jwtService.extractUserId(token);

                if (userId != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    String role = jwtService.extractRole(token);

                    List<SimpleGrantedAuthority> authorities = new ArrayList<>();
                    if (role != null && !role.trim().isEmpty()) {
                        String[] roleParts = role.split(",");
                        for (String part : roleParts) {
                            String cleanRole = part.trim().toUpperCase().replace("\"", "").replace("'", "");
                            if (!cleanRole.isEmpty()) {
                                String formattedRole = cleanRole.startsWith("ROLE_") ? cleanRole : "ROLE_" + cleanRole;
                                authorities.add(new SimpleGrantedAuthority(formattedRole));
                            }
                        }
                    }

                    if (authorities.isEmpty()) {
                        logger.warn("No valid roles/authorities extracted from JWT for userId: {}", userId);
                    }

                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(
                                    userId,
                                    null,
                                    authorities
                            );

                    authentication.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(request)
                    );

                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    logger.info("Successfully authenticated userId: {} with authorities: {}", userId, authorities);
                } else if (userId == null) {
                    logger.warn("JWT token was valid but userId could not be extracted");
                }
            }
        } catch (Exception e) {
            logger.error("Cannot set user authentication: ", e);
        }

        filterChain.doFilter(request, response);
    }

    private String extractToken(HttpServletRequest request) {
        // 1. Check Authorization Header
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null) {
            String trimmedHeader = authHeader.trim();
            if (trimmedHeader.regionMatches(true, 0, "Bearer ", 0, 7)) {
                String token = trimmedHeader.substring(7).trim();
                if (token.startsWith("\"") && token.endsWith("\"") && token.length() > 1) {
                    token = token.substring(1, token.length() - 1).trim();
                }
                if (!token.isEmpty()) {
                    return token;
                }
            }
        }

        // 2. Check Cookies (Fallback)
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("access_token".equals(cookie.getName()) && cookie.getValue() != null) {
                    String token = cookie.getValue().trim();
                    if (token.startsWith("\"") && token.endsWith("\"") && token.length() > 1) {
                        token = token.substring(1, token.length() - 1).trim();
                    }
                    if (!token.isEmpty()) {
                        return token;
                    }
                }
            }
        }

        return null;
    }

    private boolean isTokenValid(String token) {
        try {
            jwtService.extractAllClaims(token);
            return true;
        } catch (ExpiredJwtException e) {
            logger.warn("JWT expired: {}", e.getMessage());
            return false;
        } catch (Exception e) {
            logger.warn("JWT validation failed: {}", e.getMessage());
            return false;
        }
    }
}