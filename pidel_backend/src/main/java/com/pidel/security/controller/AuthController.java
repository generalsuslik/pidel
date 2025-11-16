package com.pidel.security.controller;

import com.pidel.security.dto.AuthResponseDto;
import com.pidel.security.dto.AuthUserDto;
import com.pidel.security.dto.RegistrationUserDto;
import com.pidel.security.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/sign-up")
    @Operation(
            summary = "Handler to register a user"
    )
    public ResponseEntity<AuthResponseDto> register(@RequestBody RegistrationUserDto request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/sign-up/admin")
    @Operation(
            summary = "Handler to register an admin (can be called by admin)"
    )
    public ResponseEntity<AuthResponseDto> registerAdmin(@RequestBody RegistrationUserDto request) {
        return ResponseEntity.ok(authService.registerAdmin(request));
    }

    @PostMapping("/sign-in")
    @Operation(
            summary = "Handler to sign in"
    )
    public ResponseEntity<AuthResponseDto> authenticate(@RequestBody AuthUserDto request) {
        return ResponseEntity.ok(authService.authenticate(request));
    }
}
