package com.example.todo.security;

import lombok.EqualsAndHashCode;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;

@EqualsAndHashCode
class UserPrincipalAuthenticationTokenTest {
    @Test
    void constructor_ShouldSetPrincipalAndAuthorities() {
        UserPrincipal userPrincipal = UserPrincipal.builder()
                .userId(123L)
                .username("testUser")
                .authorities(Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")))
                .build();

        UserPrincipalAuthenticationToken authenticationToken = new UserPrincipalAuthenticationToken(userPrincipal);

        Assertions.assertTrue(authenticationToken.isAuthenticated());
        Assertions.assertEquals(userPrincipal, authenticationToken.getPrincipal());
        Assertions.assertEquals(userPrincipal.getAuthorities(), authenticationToken.getAuthorities());
        Assertions.assertNull(authenticationToken.getCredentials());
    }
}
