package com.pidel.service;

import com.pidel.dto.UserDto;
import com.pidel.entity.User;
import com.pidel.security.dto.RegistrationUserDto;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface UserService {
    List<UserDto> findAll();
    UserDto findById(Long id);
    UserDto findByUsername(String username);
    User createUser(RegistrationUserDto userDto);
    User createAdmin(RegistrationUserDto userDto);
    UserDto updateUser(Long id, UserDto userDto);
    UserDto addRole(Long userId, Long roleId);
    void deleteUser(Long id);
    UserDetails loadUserByUsername(String username);
}
