package com.example.demo.security;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

    private static final Logger logger = LoggerFactory.getLogger(JwtService.class);

    private final SecretKey secretKey;
    private final long expiration;

    public JwtService(
            @Value("${app.jwt.secret}") String secret,
            @Value("${app.jwt.expiration}") long expiration
    ) {
        this.secretKey = Keys.hmacShaKeyFor(
                secret.getBytes(StandardCharsets.UTF_8)
        );

        this.expiration = expiration;
    }

    public String generateToken(Long userId, String email, String role) {

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expiration);

        return Jwts.builder()
                .subject(userId.toString())
                .claim("email", email)
                .claim("role", role)
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(secretKey)
                .compact();
    }

    public Claims extractAllClaims(String token) {

        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public Long extractUserId(String token) {
        try {
            String subject = extractAllClaims(token).getSubject();
            return subject != null ? Long.valueOf(subject) : null;
        } catch (NumberFormatException e) {
            logger.warn("Non-numeric subject in JWT token: {}", e.getMessage());
            return null;
        }
    }

    public String extractEmail(String token) {

        Object emailObj = extractAllClaims(token).get("email");
        return emailObj != null ? String.valueOf(emailObj) : null;
    }

    public String extractRole(String token) {
        Claims claims = extractAllClaims(token);
        Object roleObj = claims.get("role");
        if (roleObj == null) {
            roleObj = claims.get("roles");
        }
        if (roleObj == null) {
            roleObj = claims.get("authorities");
        }

        if (roleObj instanceof List<?> list && !list.isEmpty()) {
            return list.stream()
                    .map(String::valueOf)
                    .collect(Collectors.joining(","));
        }

        return roleObj != null ? String.valueOf(roleObj) : null;
    }

    public boolean isTokenValid(String token) {

        try {
            extractAllClaims(token);
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