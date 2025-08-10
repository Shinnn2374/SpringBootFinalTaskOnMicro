package com.example.analytics_service.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class TaskEventConsumer {
    private final AnalyticsService analyticsService;

    public TaskEventConsumer(AnalyticsService analyticsService) {
        this.analyticsService = analyticsService;
    }

    @KafkaListener(topics = "task-events", groupId = "analytics-group")
    public void handleTaskEvent(String message) {
        if (message.startsWith("TaskCompleted")) {
            Long userId = Long.parseLong(message.split(":")[1]);
            analyticsService.updateStats(userId);
        }
    }
}