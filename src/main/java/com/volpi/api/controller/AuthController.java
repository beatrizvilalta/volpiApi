package com.volpi.api.controller;
import com.volpi.api.dto.auth.AuthResponse;
import com.volpi.api.dto.auth.LoginRequest;
import com.volpi.api.dto.auth.RegisterRequest;
import com.volpi.api.model.User;
import com.volpi.api.service.AuthService;
import com.volpi.api.config.JwtConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    private final JwtConfig jwtConfig;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) throws NoSuchAlgorithmException, InvalidKeySpecException {
        User user = authService.register(request);
        return getAuthResponseResponseEntity(user);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) throws NoSuchAlgorithmException, InvalidKeySpecException {
        var user = authService.findByEmail(request.email());

        if (!authService.matchPassword(request.password(), user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        return getAuthResponseResponseEntity(user);
    }

    private ResponseEntity<AuthResponse> getAuthResponseResponseEntity(User user) throws NoSuchAlgorithmException, InvalidKeySpecException {
        var token = jwtConfig.generateToken(user.getEmail());
        return ResponseEntity.ok(new AuthResponse(token, user.getId(),
                user.getName(), user.getEmail()));
    }
}
