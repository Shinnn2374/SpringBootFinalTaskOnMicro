package com.example.auth_service.client;

import com.example.auth_service.dto.ValidationResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "user-service", path = "/api/users")
public interface UserServiceClient {

    @GetMapping("/validate-credentials")
    ValidationResponseDto validateCredentials(
            @RequestParam String username,
            @RequestParam String password);

    @GetMapping("/username/{username}/id")
    Long getUserIdByUsername(@RequestParam String username);
}