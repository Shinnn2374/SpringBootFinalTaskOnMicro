package com.example.user_service.service;

import com.example.user_service.dto.UserRequest;
import com.example.user_service.dto.UserResponse;

import java.util.List;

public interface UserService {

    UserResponse findByUsername(UserRequest request);
    
    List<UserResponse> findAll();
    
    UserResponse createUser(UserRequest request);
    
    UserResponse updateUserById(Integer id, UserRequest request);
    
    UserResponse deleteUserById(Integer id);
}
