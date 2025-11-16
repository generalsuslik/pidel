package com.pidel.security.service;

import com.pidel.entity.User;
import com.pidel.security.dto.AuthResponseDto;
import com.pidel.security.dto.AuthUserDto;
import com.pidel.security.dto.RegistrationUserDto;
import com.pidel.security.utils.JwtTokenUtils;
import com.pidel.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtils jwtTokenUtils;
    private final UserService userService;

    public AuthResponseDto register(RegistrationUserDto request) {
        User user = userService.createUser(request);
        log.info("User registered successfully: {}", request);
        return generateResponse(user);
    }

    public AuthResponseDto registerAdmin(RegistrationUserDto request) {
        User user = userService.createAdmin(request);
        log.info("Admin registered successfully: {}", request);
        return generateResponse(user);
    }

    public AuthResponseDto authenticate(AuthUserDto request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        UserDetails userDetails = userService.loadUserByUsername(request.getUsername());
        String jwtToken = jwtTokenUtils.generateToken(userDetails);
        return AuthResponseDto.builder()
                .username(userDetails.getUsername())
                .token(jwtToken)
                .build();
    }

    private AuthResponseDto generateResponse(User user) {
        String jwtToken = jwtTokenUtils.generateToken(user);
        return AuthResponseDto.builder()
                .username(user.getUsername())
                .token(jwtToken)
                .build();
    }
}
