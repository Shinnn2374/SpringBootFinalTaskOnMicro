package com.example.user_service.service;

import com.example.user_service.dao.UserRepository;
import com.example.user_service.dto.UserRegistrationDto;
import com.example.user_service.dto.UserResponseDto;
import com.example.user_service.exception.UserAlreadyExistException;
import com.example.user_service.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    public UserResponseDto registrationUser(UserRegistrationDto userRegDto) {
        log.info(MessageFormat.format("Attemping to register user {0}", userRegDto.getUsername()));
        if (userRepository.existsByUsername(userRegDto.getUsername())) {
            throw new UserAlreadyExistException(MessageFormat.format("User {0} already exists", userRegDto.getUsername()));
        }
        if (userRepository.existsByEmail(userRegDto.getEmail())) {
            throw new UserAlreadyExistException(MessageFormat.format("User {0} already exists", userRegDto.getEmail()));
        }
        User user = modelMapper.map(userRegDto, User.class);
        user.setPasswordHash(passwordEncoder.encode(userRegDto.getPassword()));

        User savedUser = userRepository.save(user);
        log.info("User registered successfully: {}", savedUser.getUsername());
        
        return modelMapper.map(savedUser, UserResponseDto.class);
    }
}
