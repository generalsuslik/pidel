package com.pidel.service;

import com.pidel.entity.User;
import com.pidel.security.dto.RegistrationUserDto;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface UserService {
    List<User> findAll();
    User findById(Long id);
    User findByUsername(String username);
    User createUser(RegistrationUserDto userDto);
    User updateUser(Long id, User userToUpdate);
    User addRole(Long userId, Long roleId);
    void deleteUser(Long id);
    UserDetails loadUserByUsername(String username);
}
