package com.example.task_service.service;

import com.example.task_service.client.UserServiceClient;
import com.example.task_service.dao.TaskRepository;
import com.example.task_service.dto.TaskDto;
import com.example.task_service.model.Task;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;

    public List<TaskDto> getTasksByUserId(Long userId) {
        return taskRepository.findByUserId(userId).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public TaskDto createTask(TaskDto TaskDto) {
        Task task = new Task();
        task.setTitle(TaskDto.getTitle());
        task.setUserId(TaskDto.getUserId());
        Task savedTask = taskRepository.save(task);
        return toDTO(savedTask);
    }

    private TaskDto toDTO(Task task) {
        TaskDto dto = new TaskDto();
        dto.setId(task.getId());
        dto.setTitle(task.getTitle());
        dto.setCompleted(task.isCompleted());
        dto.setUserId(task.getUserId());
        return dto;
    }
}