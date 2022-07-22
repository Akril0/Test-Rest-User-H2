package com.example.app.TestRestUserH2.service;

import com.example.app.TestRestUserH2.entity.User;
import com.example.app.TestRestUserH2.exceptions.UserException;
import com.example.app.TestRestUserH2.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public User saveUser(User user) throws UserException {

        Optional<User> savedUser = repository.findByPhone(user.getPhone());
        if (savedUser.isPresent()) {
            throw new UserException("User exists with given phone " +
                    user.getPhone());
        }
        return repository.save(user);
    }

    @Override
    public List<User> getAllUser() {
        return repository.findAll();
    }

    @Override
    public Optional<User> getUserById(int id) {
        return repository.findById(id);
    }

    @Override
    public User updateUser(User user) {
        return repository.save(user);
    }

    @Override
    public void deleteUser(int id) {
        repository.deleteById(id);
    }
}
