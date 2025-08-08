package com.example.task_service.controller;

import com.example.task_service.dao.TaskRepository;
import com.example.task_service.dto.TaskRequest;
import com.example.task_service.dto.User;
import com.example.task_service.feign.UserServiceClient;
import com.example.task_service.model.Task;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {
    private final TaskRepository taskRepository;
    private final UserServiceClient userServiceClient;

    @PostMapping
    @Transactional
    public ResponseEntity<Task> createTask(@RequestBody TaskRequest request) {
        ResponseEntity<User> response = userServiceClient.getUser(request.getUserId());
        if (response.getStatusCode() != HttpStatus.OK || response.getBody() == null) {
            return ResponseEntity.badRequest().build();
        }
        Task task = Task.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .userId(request.getUserId())
                .build();

        Task savedTask = taskRepository.save(task);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedTask);
    }
}