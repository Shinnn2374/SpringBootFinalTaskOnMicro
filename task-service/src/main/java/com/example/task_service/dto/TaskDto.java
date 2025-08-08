package com.example.task_service.dto;

import lombok.Data;

@Data
public class TaskDto {
    private Long id;
    private String title;
    private boolean completed;
    private Long userId;
}