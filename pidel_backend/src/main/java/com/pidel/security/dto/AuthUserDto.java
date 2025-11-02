package com.pidel.security.dto;

import lombok.*;

@Data
@Builder
public class AuthUserDto {
    private String username;
    private String password;
}