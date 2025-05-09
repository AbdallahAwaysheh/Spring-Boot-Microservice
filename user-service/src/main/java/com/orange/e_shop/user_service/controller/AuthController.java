package com.orange.e_shop.user_service.controller;

import com.orange.e_shop.user_service.dto.auth.AuthResponse;
import com.orange.e_shop.user_service.dto.auth.LoginRequest;
import com.orange.e_shop.user_service.dto.auth.PasswordResetRequest;
import com.orange.e_shop.user_service.dto.auth.RegisterRequest;
import com.orange.e_shop.user_service.model.User;
import com.orange.e_shop.user_service.service.auth.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody RegisterRequest request) {
        User savedUser = authService.register(request);
        return ResponseEntity.ok(savedUser);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }
    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String authHeader) {
        return ResponseEntity.ok(authService.logout(authHeader));
    }
    @PostMapping("/password-reset")
    public ResponseEntity<String> resetPassword(@RequestBody PasswordResetRequest request){
        return authService.resetPassword(request);
    }
}
