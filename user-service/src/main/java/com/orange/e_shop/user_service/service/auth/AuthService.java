package com.orange.e_shop.user_service.service.auth;

import com.orange.e_shop.user_service.dto.auth.*;
import com.orange.e_shop.user_service.model.User;
import com.orange.e_shop.user_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final TokenBlacklistService tokenBlacklistService;
    private final UserEventPublisher userEventPublisher;


    public User register (RegisterRequest request) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole());

        userEventPublisher.publishUserRegisteredEvent(new UserRegisterdEvent(request.getUsername(), request.getEmail()));
        return userRepository.save(user);
    }

    public AuthResponse login(LoginRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        var user = userRepository.findByUsername(request.getUsername()).orElseThrow();

        var jwt = jwtService.generateToken(user);

        return new AuthResponse(jwt);
    }

    public Object logout(String authHeader) {
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            tokenBlacklistService.blacklistToken(token);
            return ResponseEntity.ok(Map.of("message", "Successfully logged out"));
        }
        return ResponseEntity.badRequest().body(Map.of("error", "Invalid token"));
    }

    public ResponseEntity<String> resetPassword(PasswordResetRequest request) {
        log.info(request.getEmail(), " email from first reset");
        String resetToken =  jwtService.generateResetToken(request.getEmail());
        log.info(resetToken + "reset token on generation");
        if(jwtService.validateResetToken(resetToken,request.getEmail()))
        {
            log.info("token also validated after generation");
        }
        PasswordResetEvent passwordResetEvent = new PasswordResetEvent(request.getEmail(),resetToken);
        userEventPublisher.publishPasswordResetEvent(passwordResetEvent);
        return ResponseEntity.ok("Check your email :)");
    }
}
