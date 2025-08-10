package com.example.analytics_service.dao;

import com.example.analytics_service.model.TaskStat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface StatsRepository extends JpaRepository<TaskStat, Long> {
    List<TaskStat> findByUserId(Long userId);

    Optional<TaskStat> findByDateAndUserId(LocalDate now, Long userId);
}
