package com.example.demo.security;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class JwtServiceTest {

    private JwtService jwtService;
    private static final String SECRET = "012345678901234567890123456789012345678901234567890123456789"; // 60 chars

    @BeforeEach
    void setUp() {
        // 1 hour expiration
        jwtService = new JwtService(SECRET, 3600000L);
    }

    @Test
    void testGenerateAndValidateToken() {
        String token = jwtService.generateToken(123L, "user@example.com", "TEAM_MEMBER");
        assertNotNull(token);
        assertTrue(jwtService.isTokenValid(token));
        assertEquals(123L, jwtService.extractUserId(token));
        assertEquals("user@example.com", jwtService.extractEmail(token));
        assertEquals("ROLE_TEAM_MEMBER", jwtService.extractRole(token).startsWith("ROLE_") ? jwtService.extractRole(token) : "ROLE_" + jwtService.extractRole(token));
    }

    @Test
    void testTokenExpiration() throws InterruptedException {
        // Very short expiration (1 millisecond)
        JwtService shortLivedJwtService = new JwtService(SECRET, 1L);
        String token = shortLivedJwtService.generateToken(456L, "expired@example.com", "ADMIN");

        // Wait 10ms to ensure it has expired
        Thread.sleep(10);

        assertFalse(shortLivedJwtService.isTokenValid(token));
    }

    @Test
    void testInvalidTokenSignature() {
        JwtService otherJwtService = new JwtService("different-secret-key-that-is-at-least-256-bits-long-12345678", 3600000L);
        String token = otherJwtService.generateToken(789L, "other@example.com", "MANAGER");

        // Validating with jwtService (which has SECRET) should fail signature check
        assertFalse(jwtService.isTokenValid(token));
    }

    @Test
    void testMalformedToken() {
        assertFalse(jwtService.isTokenValid("not.a.valid.jwt.token"));
    }

    @Test
    void testNonNumericSubjectExtractionReturnsNullWithoutThrowing() {
        // We test extractUserId behavior when subject is not a long
        JwtService serviceWithNonNumericSubject = new JwtService(SECRET, 3600000L);
        // We can create a token with a string subject using Jwts directly
        String token = io.jsonwebtoken.Jwts.builder()
                .subject("non-numeric-uuid-or-email")
                .signWith(io.jsonwebtoken.security.Keys.hmacShaKeyFor(SECRET.getBytes(java.nio.charset.StandardCharsets.UTF_8)))
                .compact();

        assertNull(serviceWithNonNumericSubject.extractUserId(token));
    }
}
