package com.example.task_service.dto;

import lombok.Data;

@Data
public class UserDto {
    private Long id;
    private String username;
    private String email;
}