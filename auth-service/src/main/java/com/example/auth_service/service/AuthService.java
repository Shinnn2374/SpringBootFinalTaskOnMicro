package com.example.auth_service.service;

import com.example.auth_service.client.UserServiceClient;
import com.example.auth_service.configuration.JwtConfig;
import com.example.auth_service.dto.AuthRequestDto;
import com.example.auth_service.dto.AuthResponseDto;
import com.example.auth_service.dto.ValidationResponseDto;
import com.example.auth_service.exception.InvalidCredentialsException;
import com.example.auth_service.util.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final UserServiceClient userServiceClient;
    private final JwtTokenProvider jwtTokenProvider;
    private final JwtConfig jwtConfig;

    public AuthResponseDto authenticate(AuthRequestDto authRequest) {
        log.info("Authenticating user: {}", authRequest.getUsername());

        ValidationResponseDto validationResponse = userServiceClient.validateCredentials(
                authRequest.getUsername(),
                authRequest.getPassword()
        );

        if (!validationResponse.isValid()) {
            log.warn("Invalid credentials for user: {}", authRequest.getUsername());
            throw new InvalidCredentialsException("Invalid username or password");
        }

        String token = jwtTokenProvider.generateToken(
                validationResponse.getUserId(),
                validationResponse.getUsername()
        );

        log.info("User {} authenticated successfully", authRequest.getUsername());

        return new AuthResponseDto(
                token,
                "Bearer",
                jwtConfig.getExpiration() / 1000,
                validationResponse.getUserId(),
                validationResponse.getUsername()
        );
    }

    public ValidationResponseDto validateToken(String token) {
        try {
            Long userId = jwtTokenProvider.getUserIdFromToken(token);
            String username = jwtTokenProvider.getUsernameFromToken(token);

            return new ValidationResponseDto(true, userId, username, "Token is valid");
        } catch (Exception e) {
            return new ValidationResponseDto(false, null, null, e.getMessage());
        }
    }

    public Long getUserIdFromToken(String token) {
        return jwtTokenProvider.getUserIdFromToken(token);
    }
}