package com.pidel.service;

import com.pidel.dto.UserDto;
import com.pidel.entity.Role;
import com.pidel.entity.User;
import com.pidel.mapper.UserMapper;
import com.pidel.repository.UserRepository;
import com.pidel.security.dto.RegistrationUserDto;
import com.pidel.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private UserMapper userMapper;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private RoleService roleService;
    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindByIdReturnsUserDto() {
        UserDto userDto = UserDto.builder()
                .username("username")
                .build();

        User user = User.builder()
                .username("username")
                .roles(new ArrayList<>())
                .build();

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userMapper.toDto(user)).thenReturn(userDto);
        UserDto result = userService.findById(1L);
        assertEquals("username", result.getUsername());
    }

    @Test
    void testCreateUser() {
        RegistrationUserDto regDto = RegistrationUserDto.builder()
                        .username("test")
                        .password("pass")
                        .name("Test User")
                        .build();

        User user = User.builder()
                        .username("test")
                        .roles(new ArrayList<>())
                        .build();
        when(passwordEncoder.encode("pass")).thenReturn("encoded");
        when(roleService.defaultRole()).thenReturn(Role.builder().build());
        when(userRepository.save(any(User.class))).thenReturn(user);
        User result = userService.createUser(regDto);
        assertEquals("test", result.getUsername());
    }
}
