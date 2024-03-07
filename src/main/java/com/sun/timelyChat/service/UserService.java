package com.sun.timelyChat.service;

import com.sun.timelyChat.exceptions.ServiceException;
import com.sun.timelyChat.model.User;

import java.util.List;

public interface UserService {

    List<User> getAllUsers();

    User createUser(User user);

    User updateUser(User user);

    void deleteUser(Long id);

    User getUserById(Long id);

    User saveUser(User user);
}