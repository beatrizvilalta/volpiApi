package com.volpi.api.dto.auth;

import jakarta.validation.constraints.Email;

public record RegisterRequest(
        String username,
        String password,
        @Email String email
) {}