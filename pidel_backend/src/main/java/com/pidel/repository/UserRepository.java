package com.pidel.repository;

import com.pidel.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByPhoneNumber(String phone);
    Optional<User> findByUsername(String username);
}
