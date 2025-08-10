package com.example.analytics_service.controller;


import com.example.analytics_service.service.AnalyticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class WebAnalyticsController {
    private final AnalyticsService analyticsService;

    @GetMapping("/stats")
    public String getStats(@RequestParam Long userId, Model model) {
        model.addAttribute("stats", analyticsService.getStatsByUser(userId));
        model.addAttribute("userId", userId);
        return "stats";
    }
}