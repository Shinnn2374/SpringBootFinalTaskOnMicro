package com.example.task_service.dto;

import lombok.Data;

@Data
public class TaskRequest {
    private String title;
    private String description;
    private Long userId;
}