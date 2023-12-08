package com.example.todo.security;

import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class JwtPrincipalConverterTest {
    @Test
    void testConvert_ValidJwt() {
        DecodedJWT jwt = mock(DecodedJWT.class);
        when(jwt.getSubject()).thenReturn("123");
        when(jwt.getClaim("u")).thenReturn(mock(Claim.class));
        when(jwt.getClaim("u").asString()).thenReturn("testUser");
        when(jwt.getClaim("a")).thenReturn(mock(Claim.class));
        when(jwt.getClaim("a").isNull()).thenReturn(false);
        when(jwt.getClaim("a").isMissing()).thenReturn(false);
        when(jwt.getClaim("a").asList(SimpleGrantedAuthority.class)).thenReturn(Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));

        JwtPrincipalConverter converter = new JwtPrincipalConverter();

        UserPrincipal userPrincipal = converter.convert(jwt);

        assertEquals(123L, userPrincipal.getUserId());
        assertEquals("testUser", userPrincipal.getUsername());
        assertEquals(Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")), userPrincipal.getAuthorities());
    }

    @Test
    void testConvert_MissingAuthoritiesClaim() {
        DecodedJWT jwt = mock(DecodedJWT.class);
        when(jwt.getSubject()).thenReturn("123");
        when(jwt.getClaim("u")).thenReturn(mock(Claim.class));
        when(jwt.getClaim("u").asString()).thenReturn("testUser");
        when(jwt.getClaim("a")).thenReturn(mock(Claim.class));
        when(jwt.getClaim("a").isNull()).thenReturn(true);
        when(jwt.getClaim("a").isMissing()).thenReturn(true);

        JwtPrincipalConverter converter = new JwtPrincipalConverter();

        UserPrincipal userPrincipal = converter.convert(jwt);

        assertEquals(123L, userPrincipal.getUserId());
        assertEquals("testUser", userPrincipal.getUsername());
        assertEquals(Collections.emptyList(), userPrincipal.getAuthorities());
    }
}
