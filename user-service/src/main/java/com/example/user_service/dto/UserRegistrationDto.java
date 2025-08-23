package com.example.user_service.dto;

import lombok.Data;

@Data
public class UserRegistrationDto {

    private String username;
    private String email;
    private String password;
}
