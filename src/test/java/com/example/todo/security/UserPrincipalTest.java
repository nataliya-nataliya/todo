package com.example.todo.security;

import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserPrincipalTest {
    @Test
    void testUserPrincipal() {
        Long userId = 1L;
        String username = "testUser";
        String password = "testPassword";
        Collection<? extends GrantedAuthority> authorities = List.of(() -> "ROLE_USER");

        UserPrincipal userPrincipal = UserPrincipal.builder()
                .userId(userId)
                .username(username)
                .password(password)
                .authorities(authorities)
                .build();

        assertEquals(userId, userPrincipal.getUserId());
        assertEquals(username, userPrincipal.getUsername());
        assertEquals(password, userPrincipal.getPassword());
        assertEquals(authorities, userPrincipal.getAuthorities());

        assertTrue(userPrincipal.isAccountNonExpired());
        assertTrue(userPrincipal.isAccountNonLocked());
        assertTrue(userPrincipal.isCredentialsNonExpired());
        assertTrue(userPrincipal.isEnabled());
    }
}
