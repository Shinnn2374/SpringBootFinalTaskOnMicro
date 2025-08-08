package com.example.task_service.controller;

import com.example.task_service.dao.TaskRepository;
import com.example.task_service.model.Task;
import com.example.task_service.service.UserServiceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {
    private final TaskRepository taskRepository;
    private final UserServiceClient userServiceClient;

    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody Task task) {
        ResponseEntity<User> response = userServiceClient.getUser(task.getUserId());
        if (response.getStatusCode() != HttpStatus.OK) {
            return ResponseEntity.badRequest().build();
        }
        Task savedTask = taskRepository.save(task);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedTask);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTask(@PathVariable Long id) {
        return taskRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}