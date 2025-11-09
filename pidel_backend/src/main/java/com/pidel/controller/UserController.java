package com.pidel.controller;


import com.pidel.entity.User;
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
            summary = "Return list of all registered users"
    )
    public List<User> findAll() {
        return userService.findAll();
    }

    @GetMapping("/{userId}")
    @Operation(
            summary = "Return user with id = userId"
    )
    public User getUser(@PathVariable("userId") Long userId) {
        return userService.findById(userId);
    }

    @GetMapping("/{phoneNumber}")
    @Operation(
            summary = "Return user with phone number = phoneNumber"
    )
    public User getUserByPhone(@PathVariable("phoneNumber") String phoneNumber) {
        return userService.findByUsername(phoneNumber);
    }

    @PostMapping("/delete/{userId}")
    @Operation(
            summary = "Deleted user with id = userId"
    )
    public void deleteUser(@PathVariable("userId") Long userId) {
        userService.deleteUser(userId);
    }
}
