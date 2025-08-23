package com.example.user_service.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserResponseDto {
    private Long id;
    private String username;
    private String email;
    private LocalDateTime createdAt;
    private boolean enabled;
}
