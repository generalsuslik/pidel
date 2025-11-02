package com.pidel.service.impl;

import com.pidel.entity.User;
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

    private final RoleService roleService;

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
    public User findByUsername(String username) {
        return userRepository
                .findByUsername(username)
                .orElseThrow();
    }

    @Override
    public User createUser(@NonNull RegistrationUserDto userDto) {
        var user = User.builder()
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
        var user = User.builder()
                .username(userDto.getUsername())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .name(userDto.getName())
                .bonuses(0)
                .roles(roleService.findAll())
                .build();
        return userRepository.save(user);
    }

    @Override
    public User updateUser(Long id, User user) {
        return userRepository.findById(id)
                .map(userToUpdate -> {
                    userToUpdate.setUsername(user.getUsername());
                    userToUpdate.setPassword(user.getPassword());
                    userToUpdate.setBonuses(user.getBonuses());
                    userToUpdate.setName(user.getName());
                    userToUpdate.setRoles(user.getRoles());
                    return userRepository.save(userToUpdate);
                })
                .orElseGet(() -> {
                    user.setId(id);
                    return userRepository.save(user);
                });
    }

    @Override
    public User addRole(Long userId, Long roleId) {
        return userRepository.findById(userId)
                .map(user -> {
                    user.getRoles().add(roleService.findById(roleId));
                    return userRepository.save(user);
                })
                .orElseThrow();
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = findByUsername(username);

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                List.of()
        );
    }
}
