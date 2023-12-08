package com.example.todo.security;

import com.example.todo.model.User;
import com.example.todo.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class SecurityUserDetailServicesTest {
    @Mock
    private UserService userService;

    @InjectMocks
    private SecurityUserDetailServices userDetailServices;

    @Test
    void testLoadUserByUsername_UserFound() {
        String username = "testUser";
        User user = new User();
        user.setId(1L);
        user.setUsername(username);
        user.setPassword("testPassword");
        user.setRole("ROLE_USER");

        when(userService.findByUsername(username)).thenReturn(Optional.of(user));

        UserDetails userDetails = userDetailServices.loadUserByUsername(username);

        assertEquals(user.getUsername(), userDetails.getUsername());
        assertEquals(user.getPassword(), userDetails.getPassword());
        assertEquals("ROLE_USER", userDetails.getAuthorities().iterator().next().getAuthority());

        verify(userService, times(1)).findByUsername(username);
    }

    @Test
    void testLoadUserByUsername_UserNotFound() {
        String username = "nonExistingUser";

        when(userService.findByUsername(username)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> userDetailServices.loadUserByUsername(username));

        verify(userService, times(1)).findByUsername(username);
    }
}
