package com.example.task_service.controller;

import com.example.task_service.dto.TaskDto;
import com.example.task_service.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @GetMapping("/user/{userId}")
    public List<TaskDto> getTasksByUser(@PathVariable Long userId) {
        return taskService.getTasksByUserId(userId);
    }

    @PostMapping
    public TaskDto createTask(@RequestBody TaskDto taskDTO) {
        return taskService.createTask(taskDTO);
    }
}