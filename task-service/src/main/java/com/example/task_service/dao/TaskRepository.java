package com.example.task_service.dao;

import com.example.task_service.model.Task;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {
    @Lock(LockModeType.OPTIMISTIC)  // Оптимистичная блокировка
    Optional<Task> findById(Long id);
}
