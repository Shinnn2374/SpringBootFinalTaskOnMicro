package com.example.auth_service.controller;

import com.example.auth_service.dto.AuthRequestDto;
import com.example.auth_service.dto.AuthResponseDto;
import com.example.auth_service.dto.ValidationResponseDto;
import com.example.auth_service.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@Valid @RequestBody AuthRequestDto authRequest) {
        log.info("Login request for user: {}", authRequest.getUsername());
        AuthResponseDto response = authService.authenticate(authRequest);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/validate")
    public ResponseEntity<ValidationResponseDto> validateToken(@RequestHeader("Authorization") String authHeader) {
        log.debug("Token validation request");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.ok(new ValidationResponseDto(false, null, null, "Invalid authorization header"));
        }

        String token = authHeader.substring(7);
        ValidationResponseDto response = authService.validateToken(token);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/user-id")
    public ResponseEntity<Long> getUserIdFromToken(@RequestHeader("Authorization") String authHeader) {
        log.debug("Get user ID from token request");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.badRequest().build();
        }

        String token = authHeader.substring(7);
        try {
            Long userId = authService.getUserIdFromToken(token);
            return ResponseEntity.ok(userId);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}