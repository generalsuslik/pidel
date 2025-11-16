package com.pidel.controller;


import com.pidel.dto.UserDto;
import com.pidel.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    @Operation(
            summary = "Returns list of all registered users"
    )
    public List<UserDto> findAll() {
        return userService.findAll();
    }

    @GetMapping("/{userId}")
    @Operation(
            summary = "Returns user with id = userId"
    )
    public UserDto getUser(@PathVariable("userId") Long userId) {
        return userService.findById(userId);
    }

    @GetMapping("/{phoneNumber}")
    @Operation(
            summary = "Returns user with phone number = phoneNumber"
    )
    public UserDto getUserByPhone(@PathVariable("phoneNumber") String phoneNumber) {
        return userService.findByUsername(phoneNumber);
    }

    @PostMapping("/delete/{userId}")
    @Operation(
            summary = "Deletes user with id = userId"
    )
    public void deleteUser(@PathVariable("userId") Long userId) {
        userService.deleteUser(userId);
    }
}
