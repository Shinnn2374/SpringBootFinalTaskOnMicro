package com.example.task_service.client;

import com.example.task_service.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "USER-SERVICE")
public interface UserServiceClient {
    @GetMapping("/api/users/{userId}")
    UserDto getUserById(@PathVariable Long userId);
}