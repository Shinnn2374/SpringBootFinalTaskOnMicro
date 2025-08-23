package com.example.task_service.service;

import com.example.task_service.dao.TaskRepository;
import com.example.task_service.dto.TaskRequestDto;
import com.example.task_service.dto.TaskResponseDto;
import com.example.task_service.dto.TaskStatusUpdateDto;
import com.example.task_service.exception.TaskNotFoundException;
import com.example.task_service.exception.UnauthorizedAccessException;
import com.example.task_service.model.Task;
import com.example.task_service.model.TaskStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@CacheConfig(cacheNames = "userTasks")
public class TaskService {

    private final TaskRepository taskRepository;

    @Transactional
    @Caching(evict = {
            @CacheEvict(key = "#userId"),
            @CacheEvict(key = "#userId + '_stats'")
    })
    public TaskResponseDto createTask(TaskRequestDto requestDto, Long userId) {
        log.info("Creating task for user: {}", userId);

        Task task = new Task();
        task.setTitle(requestDto.getTitle());
        task.setDescription(requestDto.getDescription());
        task.setPriority(requestDto.getPriority());
        task.setDueDate(requestDto.getDueDate());
        task.setUserId(userId);

        Task savedTask = taskRepository.save(task);
        log.info("Task created successfully with id: {}", savedTask.getId());

        return convertToDto(savedTask);
    }

    @Transactional(readOnly = true)
    @Cacheable(key = "#userId")
    public List<TaskResponseDto> getUserTasks(Long userId) {
        log.info("Fetching all tasks for user: {} (from database)", userId);

        List<Task> tasks = taskRepository.findByUserIdOrderByCreatedAtDesc(userId);
        return tasks.stream()
                .map(this::convertToDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<TaskResponseDto> getUserTasksByStatus(Long userId, TaskStatus status) {
        log.info("Fetching {} tasks for user: {}", status, userId);

        List<Task> tasks = taskRepository.findByUserIdAndStatusOrderByCreatedAtDesc(userId, status);
        return tasks.stream()
                .map(this::convertToDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public TaskResponseDto getTaskById(Long taskId, Long userId) {
        log.info("Fetching task {} for user: {}", taskId, userId);

        Task task = taskRepository.findByIdAndUserId(taskId, userId)
                .orElseThrow(() -> new TaskNotFoundException("Task not found with id: " + taskId));

        return convertToDto(task);
    }

    @Transactional
    @Caching(evict = {
            @CacheEvict(key = "#userId"),
            @CacheEvict(key = "#userId + '_stats'")
    })
    public TaskResponseDto updateTask(Long taskId, TaskRequestDto requestDto, Long userId) {
        log.info("Updating task {} for user: {}", taskId, userId);

        Task task = taskRepository.findByIdAndUserId(taskId, userId)
                .orElseThrow(() -> new TaskNotFoundException("Task not found with id: " + taskId));

        task.setTitle(requestDto.getTitle());
        task.setDescription(requestDto.getDescription());
        task.setPriority(requestDto.getPriority());
        task.setDueDate(requestDto.getDueDate());

        Task updatedTask = taskRepository.save(task);
        log.info("Task {} updated successfully", taskId);

        return convertToDto(updatedTask);
    }

    @Transactional
    @Caching(evict = {
            @CacheEvict(key = "#userId"),
            @CacheEvict(key = "#userId + '_stats'")
    })
    public TaskResponseDto updateTaskStatus(Long taskId, TaskStatusUpdateDto statusDto, Long userId) {
        log.info("Updating status of task {} for user: {}", taskId, userId);

        Task task = taskRepository.findByIdAndUserId(taskId, userId)
                .orElseThrow(() -> new TaskNotFoundException("Task not found with id: " + taskId));

        task.setStatus(statusDto.getStatus());

        Task updatedTask = taskRepository.save(task);
        log.info("Task {} status updated to {}", taskId, statusDto.getStatus());

        return convertToDto(updatedTask);
    }

    @Transactional
    @Caching(evict = {
            @CacheEvict(key = "#userId"),
            @CacheEvict(key = "#userId + '_stats'")
    })
    public void deleteTask(Long taskId, Long userId) {
        log.info("Deleting task {} for user: {}", taskId, userId);

        if (!taskRepository.existsByIdAndUserId(taskId, userId)) {
            throw new TaskNotFoundException("Task not found with id: " + taskId);
        }

        taskRepository.deleteById(taskId);
        log.info("Task {} deleted successfully", taskId);
    }

    @Transactional(readOnly = true)
    @Cacheable(key = "#userId + '_stats'")
    public TaskStats getTaskStats(Long userId) {
        log.info("Fetching task statistics for user: {} (from database)", userId);

        long totalTasks = taskRepository.countTotalTasksByUserId(userId);
        long completedTasks = taskRepository.countCompletedTasksByUserId(userId);

        return new TaskStats(totalTasks, completedTasks, totalTasks - completedTasks);
    }

    public void validateTaskOwnership(Long taskId, Long userId) {
        if (!taskRepository.existsByIdAndUserId(taskId, userId)) {
            throw new UnauthorizedAccessException("User " + userId + " doesn't have access to task " + taskId);
        }
    }

    private TaskResponseDto convertToDto(Task task) {
        TaskResponseDto dto = new TaskResponseDto();
        dto.setId(task.getId());
        dto.setTitle(task.getTitle());
        dto.setDescription(task.getDescription());
        dto.setStatus(task.getStatus());
        dto.setPriority(task.getPriority());
        dto.setDueDate(task.getDueDate());
        dto.setUserId(task.getUserId());
        dto.setCreatedAt(task.getCreatedAt());
        dto.setUpdatedAt(task.getUpdatedAt());
        return dto;
    }

    public record TaskStats(long totalTasks, long completedTasks, long pendingTasks) {}
}