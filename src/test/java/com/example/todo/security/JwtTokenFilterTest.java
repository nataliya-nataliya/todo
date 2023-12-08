package com.example.todo.security;

import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class JwtTokenFilterTest {

    @Test
    void testExtractTokenFromRequest_ValidToken() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getHeader("Authorization")).thenReturn("Bearer testToken");

        JwtDecoder mockJwtDecoder = mock(JwtDecoder.class);
        JwtPrincipalConverter mockJwtPrincipalConverter = mock(JwtPrincipalConverter.class);

        JwtTokenFilter jwtTokenFilter = new JwtTokenFilter(mockJwtDecoder, mockJwtPrincipalConverter);

        Optional<String> token = jwtTokenFilter.extractTokenFromRequest(request);

        assertTrue(token.isPresent());
        assertEquals("testToken", token.get());
    }

    @Test
    void testExtractTokenFromRequest_InvalidToken() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getHeader("Authorization")).thenReturn("InvalidToken");

        JwtDecoder mockJwtDecoder = mock(JwtDecoder.class);
        JwtPrincipalConverter mockJwtPrincipalConverter = mock(JwtPrincipalConverter.class);

        JwtTokenFilter jwtTokenFilter = new JwtTokenFilter(mockJwtDecoder, mockJwtPrincipalConverter);

        Optional<String> token = jwtTokenFilter.extractTokenFromRequest(request);

        assertFalse(token.isPresent());
    }

    @Test
    void testExtractTokenFromRequest_NoAuthorizationHeader() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getHeader("Authorization")).thenReturn(null);

        JwtDecoder mockJwtDecoder = mock(JwtDecoder.class);
        JwtPrincipalConverter mockJwtPrincipalConverter = mock(JwtPrincipalConverter.class);

        JwtTokenFilter jwtTokenFilter = new JwtTokenFilter(mockJwtDecoder, mockJwtPrincipalConverter);

        Optional<String> token = jwtTokenFilter.extractTokenFromRequest(request);

        assertFalse(token.isPresent());
    }
}
