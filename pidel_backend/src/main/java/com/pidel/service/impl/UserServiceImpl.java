package com.pidel.service.impl;

import com.pidel.entity.User;
import com.pidel.repository.UserRepository;
import com.pidel.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findById(Long id) {
        return userRepository
                .findById(id)
                .orElseThrow();
    }

    @Override
    public User findByPhone(String phone) {
        return userRepository
                .findByPhoneNumber(phone)
                .orElseThrow();
    }

    @Override
    public User findByUsername(String username) {
        return userRepository
                .findByUsername(username)
                .orElseThrow();
    }

    @Override
    public void createUser(User user) {
        userRepository.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
