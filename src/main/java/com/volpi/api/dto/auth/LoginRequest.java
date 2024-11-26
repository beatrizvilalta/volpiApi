package com.volpi.api.dto.auth;

public record LoginRequest(
        String username,
        String password
) {}
