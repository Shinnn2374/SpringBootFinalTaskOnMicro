package com.example.task_service.dto;

import com.example.task_service.model.Priority;
import com.example.task_service.model.TaskStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TaskResponseDto {
    private Long id;
    private String title;
    private String description;
    private TaskStatus status;
    private Priority priority;
    private LocalDateTime dueDate;
    private Long userId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}