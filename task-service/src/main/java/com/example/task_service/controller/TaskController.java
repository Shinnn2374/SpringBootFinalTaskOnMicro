package com.example.task_service.controller;

import com.example.task_service.dao.TaskRepository;
import com.example.task_service.dto.User;
import com.example.task_service.feign.UserServiceClient;
import com.example.task_service.model.Task;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Task> createTask(@RequestBody Task task) {
        User user = userServiceClient.getUser(task.getUserId());
        if (user == null) {
            return ResponseEntity.badRequest().build();
        }

        Task savedTask = taskRepository.save(task);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedTask);
    }
}