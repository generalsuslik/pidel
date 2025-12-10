package com.pidel.service.impl;

import com.pidel.dto.UserDto;
import com.pidel.entity.Role;
import com.pidel.entity.User;
import com.pidel.mapper.UserMapper;
import com.pidel.repository.UserRepository;
import com.pidel.security.dto.RegistrationUserDto;
import com.pidel.service.RoleService;
import com.pidel.service.UserService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    private final RoleService roleService;

    @Override
    public List<UserDto> findAll() {
        return userMapper.toDtoList(userRepository.findAll());
    }

    @Override
    public UserDto findById(Long id) {
        User entity = userRepository
                .findById(id)
                .orElseThrow();
        return userMapper.toDto(entity);
    }

    @Override
    public UserDto findByUsername(String username) {
        User entity = userRepository
                .findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return userMapper.toDto(entity);
    }

    @Override
    public User createUser(@NonNull RegistrationUserDto userDto) {
        User user = User.builder()
                .username(userDto.getUsername())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .name(userDto.getName())
                .bonuses(0)
                .roles(List.of(roleService.defaultRole()))
                .build();
        return userRepository.save(user);
    }

    @Override
    public User createAdmin(@NonNull RegistrationUserDto userDto) {
        User user = User.builder()
                .username(userDto.getUsername())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .name(userDto.getName())
                .bonuses(0)
                .roles(roleService.findAll())
                .build();
        return userRepository.save(user);
    }

    @Override
    public UserDto updateUser(Long id, UserDto userDto) {
        User userToUpdate = userRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        userMapper.updateUserFromDto(userDto, userToUpdate);
        User updatedUser = userRepository.save(userToUpdate);

        return userMapper.toDto(updatedUser);
    }

    @Override
    public UserDto addRole(Long userId, Long roleId) {
        User user = userRepository
                .findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Role role = roleService.findById(roleId);
        if (user.getRoles().contains(role)) {
            return userMapper.toDto(user);
        }

        user.getRoles().add(role);
        User updatedUser = userRepository.save(user);

        return userMapper.toDto(updatedUser);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository
                .findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                user.getAuthorities()
        );
    }

    @Override
    public String getAddress(String username) {
        User user = userRepository
                .findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getAddress() == null) {
            throw new RuntimeException("Address is null");
        }
        return user.getAddress();
    }
}
