package com.pidel.security.dto;

import jakarta.annotation.Nullable;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegistrationUserDto {
    private String username;
    private String password;
    private String confirmedPassword;

    @Nullable
    private String name;
}
