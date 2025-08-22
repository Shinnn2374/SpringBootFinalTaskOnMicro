package com.example.user_service.service;

import com.example.user_service.dao.UserRepository;
import com.example.user_service.dto.UserRequest;
import com.example.user_service.dto.UserResponse;
import com.example.user_service.exception.UserNotFoundException;
import com.example.user_service.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
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
        return null;
    }

    @Override
    public UserResponse createUser(UserRequest request) {
        var response = UserResponse.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password("12345")
                .phoneNumber("phonenumber")
                .build();
        userRepository.save(UserResponse.dtoToModel(response));
        return response;
    }

    @Override
    public UserResponse updateUserById(Integer id, UserRequest request) {
        var oldUser = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(MessageFormat.format("User with id {0} not found", id)));
        oldUser.setUsername(request.getUsername());
        oldUser.setEmail(request.getEmail());
        return null;
    }

    @Override
    public UserResponse deleteUserById(Integer id) {
        return null;
    }
}
