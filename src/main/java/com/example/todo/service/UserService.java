package com.example.todo.service;

import com.example.todo.model.User;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Getter
public class UserService {

    @Value("${user.username}")
    private String userUsername;

    @Value("${user.password}")
    private String userPassword;

    public UserService(String userUsername, String userPassword) {
        this.userUsername = userUsername;
        this.userPassword = userPassword;
    }

    public UserService() {
    }

    public Optional<User> findByUsername(String username) {
        if (username.equalsIgnoreCase(userUsername)) {
            var user = new User();
            user.setId(1L);
            user.setUsername(userUsername);
            user.setPassword(userPassword);
            user.setRole("ROLE_ADMIN");
            return Optional.of(user);
        }
        return Optional.empty();
    }
}
