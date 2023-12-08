package com.example.todo.service;

import com.example.todo.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserService userService;

    @Test
    void testFindByUsername_IgnoreCase() {
        String username = "testUser";
        User mockUser = new User();
        mockUser.setId(1L);
        mockUser.setUsername(username);
        mockUser.setPassword("mockedPassword");
        mockUser.setRole("ROLE_ADMIN");

        when(userService.findByUsername(anyString())).thenReturn(Optional.of(mockUser));

        Optional<User> userOptional = userService.findByUsername("TESTUSER");

        assertTrue(userOptional.isPresent());
        User user = userOptional.get();
        assertEquals(username, user.getUsername());
    }

    @Test
    void testFindByUsername_NonExistingUser() {
        String nonExistingUsername = "nonExistingUser";

        when(userService.findByUsername(nonExistingUsername)).thenReturn(Optional.empty());

        Optional<User> userOptional = userService.findByUsername(nonExistingUsername);

        assertFalse(userOptional.isPresent());
    }

    @Test
    void testFindByUsername() {
        String userUsername = "testUser";
        String userPassword = "mockedPassword";

        UserService userService = new UserService(userUsername, userPassword);

        String username = "testUser";

        User expectedUser = userService.findByUsername(username).orElse(null);

        assertNotNull(expectedUser);
        assertEquals(username, expectedUser.getUsername());
        assertEquals(userPassword, expectedUser.getPassword());
        assertEquals("ROLE_ADMIN", expectedUser.getRole());
    }

    @Test
    void testUserEquals() {
        User user1 = new User(1L, "User 1", "password1", "ROLE_ADMIN");
        User user2 = new User(2L, "User 1", "password2", "ROLE_ADMIN");

        assertEquals(user1, user2);
        assertEquals(user1.hashCode(), user2.hashCode());
    }
}
