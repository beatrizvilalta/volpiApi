package com.volpi.api.dto.auth;

public record LoginRequest(
        String email,
        String password
) {}
