package com.example.task_service.dto;

import com.example.task_service.model.TaskStatus;
import lombok.Data;

@Data
public class TaskStatusUpdateDto {

    private TaskStatus taskStatus;
}
