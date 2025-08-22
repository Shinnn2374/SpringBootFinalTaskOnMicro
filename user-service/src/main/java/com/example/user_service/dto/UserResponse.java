package com.example.user_service.dto;

import com.example.user_service.model.User;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponse {
    private String username;
    private String email;
    private String phoneNumber;
    private String password;

    public static User dtoToModel(UserResponse userResponse) {
        var user = new User();
        user.setUsername(userResponse.getUsername());
        user.setEmail(userResponse.getEmail());
        user.setPhoneNumber(userResponse.getPhoneNumber());
        user.setPassword(userResponse.getPassword());
        return user;
    }
}
