package com.example.analytics_service.dao;

import com.example.analytics_service.model.TaskStat;

public interface StatsRepository extends JpaRepository<TaskStat, Long> {
    List<TaskStat> findByUserId(Long userId);
}
