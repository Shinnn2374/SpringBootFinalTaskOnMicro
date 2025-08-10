package com.example.analytics_service.service;


import com.example.analytics_service.dao.StatsRepository;
import com.example.analytics_service.model.TaskStat;
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