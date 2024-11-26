package com.volpi.api.controller;
import com.volpi.api.dto.auth.AuthResponse;
import com.volpi.api.dto.auth.LoginRequest;
import com.volpi.api.dto.auth.RegisterRequest;
import com.volpi.api.model.User;
import com.volpi.api.service.AuthService;
import com.volpi.api.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    private final JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        User user = authService.register(request);
        return getAuthResponseResponseEntity(user);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        var user = authService.findByEmail(request.email());

        if (!authService.matchPassword(request.password(), user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        return getAuthResponseResponseEntity(user);
    }

    private ResponseEntity<AuthResponse> getAuthResponseResponseEntity(User user) {
        var token = jwtUtil.generateToken(user.getEmail());
        return ResponseEntity.ok(new AuthResponse(token, user.getId(),
                user.getName(), user.getEmail()));
    }
}
