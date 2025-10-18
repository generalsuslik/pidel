package com.pidel.service;

import com.pidel.entity.User;

import java.util.List;

public interface UserService {
    List<User> findAll();
    User findById(Long id);
    User findByPhone(String phone);
    User findByUsername(String username);
    void createUser(User user);
    void deleteUser(Long id);
}
