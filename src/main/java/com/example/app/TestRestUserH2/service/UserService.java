package com.example.app.TestRestUserH2.service;

import com.example.app.TestRestUserH2.entity.User;
import com.example.app.TestRestUserH2.exceptions.UserException;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User saveUser(User user) throws UserException;

    List<User> getAllUser();

    Optional<User> getUserById(int id);

    User updateUser(User user);

    void deleteUser(int id);
}
