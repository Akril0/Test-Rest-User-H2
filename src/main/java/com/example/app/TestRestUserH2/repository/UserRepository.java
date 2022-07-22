package com.example.app.TestRestUserH2.repository;

import com.example.app.TestRestUserH2.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByPhone(String phone);
}
