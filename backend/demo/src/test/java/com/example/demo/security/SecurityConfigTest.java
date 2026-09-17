package com.example.demo.security;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

class SecurityConfigTest {

    private SecurityConfig securityConfig;

    @BeforeEach
    void setUp() {
        JwtAuthenticationFilter jwtFilter = mock(JwtAuthenticationFilter.class);
        securityConfig = new SecurityConfig(jwtFilter);
        SecurityContextHolder.clearContext();
    }

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void testAuthenticationEntryPointReturnsDiagnosticJson() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest("POST", "/api/member/reports");
        MockHttpServletResponse response = new MockHttpServletResponse();

        securityConfig.authenticationEntryPoint().commence(
                request,
                response,
                new InsufficientAuthenticationException("Full authentication is required")
        );

        assertEquals(401, response.getStatus());
        assertTrue(response.getContentType().contains("application/json"));
        String body = response.getContentAsString();
        assertTrue(body.contains("\"status\":401"));
        assertTrue(body.contains("\"error\":\"Unauthorized\""));
        assertTrue(body.contains("\"path\":\"/api/member/reports\""));
        assertTrue(body.contains("\"method\":\"POST\""));
        assertTrue(body.contains("Full authentication is required"));
    }

    @Test
    void testAccessDeniedHandlerWithAuthenticatedUserReturnsDiagnosticJson() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest("POST", "/api/member/reports");
        MockHttpServletResponse response = new MockHttpServletResponse();

        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                42L,
                null,
                List.of(new SimpleGrantedAuthority("ROLE_GUEST"))
        );
        SecurityContextHolder.getContext().setAuthentication(auth);

        securityConfig.accessDeniedHandler().handle(
                request,
                response,
                new AccessDeniedException("Access Denied: Insufficient permissions")
        );

        assertEquals(403, response.getStatus());
        assertTrue(response.getContentType().contains("application/json"));
        String body = response.getContentAsString();
        assertTrue(body.contains("\"status\":403"));
        assertTrue(body.contains("\"error\":\"Forbidden\""));
        assertTrue(body.contains("\"path\":\"/api/member/reports\""));
        assertTrue(body.contains("\"method\":\"POST\""));
        assertTrue(body.contains("\"user\":\"42\""));
        assertTrue(body.contains("\"authorities\":[\"ROLE_GUEST\"]"));
        assertTrue(body.contains("Access Denied: Insufficient permissions"));
    }

    @Test
    void testAccessDeniedHandlerWithAnonymousUser() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/api/member/reports");
        MockHttpServletResponse response = new MockHttpServletResponse();

        securityConfig.accessDeniedHandler().handle(
                request,
                response,
                new AccessDeniedException("Access is denied")
        );

        assertEquals(403, response.getStatus());
        String body = response.getContentAsString();
        assertTrue(body.contains("\"user\":\"anonymous\""));
        assertTrue(body.contains("\"authorities\":[]"));
    }
}
