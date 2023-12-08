package com.example.todo.service;

import com.example.todo.model.LoginResponse;
import com.example.todo.security.JwtTokenProvider;
import com.example.todo.security.UserPrincipal;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthService authService;

    @Test
    void testAttemptLogin_Success() {
        String username = "testUser";
        String password = "testPassword";

        Authentication authentication = Mockito.mock(Authentication.class);
        Mockito.when(authentication.getPrincipal()).thenReturn(new UserPrincipal(1L, username, "password", Collections.emptyList()));

        Mockito.when(authenticationManager.authenticate(Mockito.any()))
                .thenReturn(authentication);

        Mockito.when(jwtTokenProvider.generateToken(Mockito.anyLong(), Mockito.eq(username), Mockito.any()))
                .thenReturn("testToken");

        LoginResponse loginResponse = authService.attemptLogin(username, password);

        assertEquals("testToken", loginResponse.getAccessToken());
    }

    @Test
    void testAttemptLogin_Failure() {
        String username = "testUser";
        String password = "invalidPassword";

        Mockito.when(authenticationManager.authenticate(Mockito.any()))
                .thenThrow(new AuthenticationException("Invalid credentials") {
                });

        assertThrows(BadCredentialsException.class, () -> authService.attemptLogin(username, password));
    }
}
