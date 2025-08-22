package com.example.user_service.model;

import com.example.user_service.dto.UserResponse;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "users")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String username;
    private String email;
    private String phoneNumber;
    private String password;

    public static UserResponse userToDto(User user) {
        var response = UserResponse.builder()
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .username(user.getUsername())
                .password(user.getPassword())
                .build();
        return response;
    }
}
