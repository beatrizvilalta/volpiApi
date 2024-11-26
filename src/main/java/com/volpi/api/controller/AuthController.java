package com.volpi.api.controller;
import com.volpi.api.dto.auth.AuthResponse;
import com.volpi.api.dto.auth.LoginRequest;
import com.volpi.api.dto.auth.RegisterRequest;
import com.volpi.api.model.User;
import com.volpi.api.repository.UserRepository;
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

    private final UserRepository userRepository;

    private final JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody RegisterRequest request) {
        User user = authService.register(request);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        var user = userRepository.findByUsername(request.username())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!authService.matchPassword(request.password(), user.getPassword())) {
            return ResponseEntity.badRequest().body(null);
        }

        var token = jwtUtil.generateToken(user.getUsername());
        return ResponseEntity.ok(new AuthResponse(token));
    }
}
