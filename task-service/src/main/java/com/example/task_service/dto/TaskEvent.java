package com.example.task_service.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TaskEvent {
    private Long taskId;
    private String title;
    private LocalDateTime deadline;
    private Long userId;
}
