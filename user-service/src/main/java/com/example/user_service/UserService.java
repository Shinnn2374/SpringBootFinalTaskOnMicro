package com.example.user_service;

import com.example.user_service.dao.UserRepository;
import com.example.user_service.dto.UserResponseDto;
import com.example.user_service.util.CustomPasswordEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final CustomPasswordEncoder customPasswordEncoder;

    public UserResponseDto registrationUser(UserResponseDto userDto) {
        return null;
    }
}
