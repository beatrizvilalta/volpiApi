package com.volpi.api.dto.auth;

public record AuthResponse(
        String token,
        Long userId,
        String name,
        String email
) {}
