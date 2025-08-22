package com.example.user_service.service;

import com.example.user_service.dao.UserRepository;
import com.example.user_service.dto.UserRequest;
import com.example.user_service.dto.UserResponse;
import com.example.user_service.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl  implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserResponse findByUsername(UserRequest request) {
        return null;
    }

    @Override
    public List<UserResponse> findAll() {
        return userRepositor;
    }

    @Override
    public UserResponse createUser(UserRequest request) {
        return null;
    }

    @Override
    public UserResponse updateUserById(Integer id, UserRequest request) {
        return null;
    }

    @Override
    public UserResponse deleteUserById(Integer id) {
        return null;
    }
}
