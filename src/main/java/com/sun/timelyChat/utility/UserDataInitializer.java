package com.sun.timelyChat.utility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.sun.timelyChat.model.User;
import com.sun.timelyChat.service.UserService;

import jakarta.annotation.PostConstruct;

@Configuration
public class UserDataInitializer {

    @Autowired
    private UserService userService; // Assuming UserService injects UserRepository

    @PostConstruct
    public void init() {
        // Create some initial users (avoid storing passwords in plain text)
        User user1 = new User("john.doe", "john.doe@example.com");
        // Set a secure password using a separate method (not shown here)
        userService.createUser(user1);

        // Similarly create other users
    }
}