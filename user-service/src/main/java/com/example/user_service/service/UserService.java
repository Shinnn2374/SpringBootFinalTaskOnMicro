package com.example.user_service.service;

import com.example.user_service.dao.UserRepository;
import com.example.user_service.dto.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder customPasswordEncoder;
    private final ModelMapper modelMapper;

    public UserResponseDto registrationUser(UserResponseDto userDto) {
        return null;
    }
}
