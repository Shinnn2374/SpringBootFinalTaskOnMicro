package com.example.task_service.controller;

import com.example.task_service.dto.TaskRequestDto;
import com.example.task_service.dto.TaskResponseDto;
import com.example.task_service.dto.TaskStatusUpdateDto;
import com.example.task_service.model.TaskStatus;
import com.example.task_service.service.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
@Slf4j
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    public ResponseEntity<TaskResponseDto> createTask(
            @RequestBody TaskRequestDto requestDto,
            @RequestHeader("X-User-Id") Long userId) {

        log.info("Creating task for user: {}", userId);
        TaskResponseDto createdTask = taskService.createTask(requestDto, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTask);
    }

    @GetMapping
    public ResponseEntity<List<TaskResponseDto>> getUserTasks(
            @RequestHeader("X-User-Id") Long userId) {

        log.info("Fetching all tasks for user: {}", userId);
        List<TaskResponseDto> tasks = taskService.getUserTasks(userId);
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<TaskResponseDto>> getUserTasksByStatus(
            @PathVariable TaskStatus status,
            @RequestHeader("X-User-Id") Long userId) {

        log.info("Fetching {} tasks for user: {}", status, userId);
        List<TaskResponseDto> tasks = taskService.getUserTasksByStatus(userId, status);
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskResponseDto> getTaskById(
            @PathVariable Long id,
            @RequestHeader("X-User-Id") Long userId) {

        log.info("Fetching task {} for user: {}", id, userId);
        TaskResponseDto task = taskService.getTaskById(id, userId);
        return ResponseEntity.ok(task);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskResponseDto> updateTask(
            @PathVariable Long id,
            @RequestBody TaskRequestDto requestDto,
            @RequestHeader("X-User-Id") Long userId) {

        log.info("Updating task {} for user: {}", id, userId);
        TaskResponseDto updatedTask = taskService.updateTask(id, requestDto, userId);
        return ResponseEntity.ok(updatedTask);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<TaskResponseDto> updateTaskStatus(
            @PathVariable Long id,
            @RequestBody TaskStatusUpdateDto statusDto,
            @RequestHeader("X-User-Id") Long userId) {

        log.info("Updating status of task {} for user: {}", id, userId);
        TaskResponseDto updatedTask = taskService.updateTaskStatus(id, statusDto, userId);
        return ResponseEntity.ok(updatedTask);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(
            @PathVariable Long id,
            @RequestHeader("X-User-Id") Long userId) {

        log.info("Deleting task {} for user: {}", id, userId);
        taskService.deleteTask(id, userId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/stats")
    public ResponseEntity<TaskService.TaskStats> getTaskStats(
            @RequestHeader("X-User-Id") Long userId) {

        log.info("Fetching task statistics for user: {}", userId);
        TaskService.TaskStats stats = taskService.getTaskStats(userId);
        return ResponseEntity.ok(stats);
    }
}