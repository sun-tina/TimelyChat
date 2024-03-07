package com.sun.timelyChat.service;

import com.sun.timelyChat.exceptions.ServiceException;
import com.sun.timelyChat.model.User;
import com.sun.timelyChat.repository.*;
import com.sun.timelyChat.utility.PasswordUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User createUser(User user) throws ServiceException {
        try {
            if (userRepository.existsByUsername(user.getUsername())) {
                throw new ServiceException("Username already exists");
            }
            if (userRepository.existsByEmail(user.getEmail())) {
                throw new ServiceException("Email already exists");
            }
            user.setPassword(PasswordUtil.hashPassword(user.getPassword())); // Use password hashing utility
            return userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            // Handle potential constraint violations (e.g., unique username/email)
            throw new ServiceException("Failed to create user: " + e.getMessage());
        }
    }

    @SuppressWarnings("null")
    @Override
    public User updateUser(User user) throws ServiceException {
        @SuppressWarnings("null")
        Optional<User> existingUser = userRepository.findById(user.getId());
        if (!existingUser.isPresent()) {
            throw new ServiceException("User not found");
        }

        // Update user details (excluding password for security reasons)
        existingUser.get().setUsername(user.getUsername());
        existingUser.get().setEmail(user.getEmail());

        try {
            return userRepository.save(existingUser.get());
        } catch (DataIntegrityViolationException e) {
            // Handle potential constraint violations (e.g., unique username/email)
            throw new ServiceException("Failed to update user: " + e.getMessage());
        }
    }

    @SuppressWarnings("null")
    @Override
    public void deleteUser(Long id) throws ServiceException {
        if (!userRepository.existsById(id)) {
            throw new ServiceException("User not found");
        }
        try {
            userRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            // Handle potential constraint violations (e.g., foreign key constraints)
            throw new ServiceException("Failed to delete user: " + e.getMessage());
        }
    }

    @Override
    public User getUserById(Long id) throws ServiceException {
        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent()) {
            throw new ServiceException("User not found");
        }
        return user.get();
    }

    @Override
    public User saveUser(User user) throws ServiceException {
        return userRepository.save(user);
    }
}
