package com.example.analytics_service.controller;


import com.example.analytics_service.dto.StatsDto;
import com.example.analytics_service.service.AnalyticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stats")
@RequiredArgsConstructor
public class AnalyticsController {
    private final AnalyticsService analyticsService;

    @GetMapping("/user/{userId}")
    public List<StatsDto> getStats(@PathVariable Long userId) {
        return analyticsService.getStatsByUser(userId);
    }
}