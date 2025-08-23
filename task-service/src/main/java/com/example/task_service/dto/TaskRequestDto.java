package com.example.task_service.dto;

import com.example.task_service.model.Priority;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TaskRequestDto {

    private String title;
    private String description;
    private Priority priority = Priority.MEDIUM;
    private LocalDateTime dueDate;
}
