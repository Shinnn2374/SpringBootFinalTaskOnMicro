package com.example.user_service.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponse {
    private String username;
    private String email;
    private String phoneNumber;
    private String password;
}
