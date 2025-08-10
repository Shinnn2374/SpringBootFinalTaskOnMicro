package com.example.analytics_service.service;

import com.example.analyticsservice.model.TaskStat;
import com.example.analyticsservice.repository.StatsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class AnalyticsService {
    private final StatsRepository statsRepository;

    public void updateStats(Long userId) {
        TaskStat todayStat = statsRepository
                .findByDateAndUserId(LocalDate.now(), userId)
                .orElseGet(() -> new TaskStat(LocalDate.now(), 0, userId));

        todayStat.setCompletedTasks(todayStat.getCompletedTasks() + 1);
        statsRepository.save(todayStat);
    }
}