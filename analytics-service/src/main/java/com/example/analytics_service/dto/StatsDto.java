package com.example.analytics_service.dto;


import lombok.Data;

import java.time.LocalDate;

@Data
public class StatsDto {
    private LocalDate date;
    private int completedTasks;
    private Long userId;
}
