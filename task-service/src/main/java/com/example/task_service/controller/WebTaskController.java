package com.example.task_service.controller;

import com.example.task_service.dto.TaskDto;
import com.example.task_service.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class WebTaskController {
    private final TaskService taskService;

    @GetMapping("/tasks")
    public String getTasks(@RequestParam Long userId, Model model) {
        model.addAttribute("tasks", taskService.getTasksByUserId(userId));
        model.addAttribute("userId", userId);
        return "tasks";
    }

    @GetMapping("/tasks/new")
    public String showCreateForm(@RequestParam Long userId, Model model) {
        model.addAttribute("task", new TaskDto());
        model.addAttribute("userId", userId);
        return "createTask";
    }

    @PostMapping("/tasks")
    public String createTask(@ModelAttribute TaskDto taskDTO, @RequestParam Long userId) {
        taskDTO.setUserId(userId);
        taskService.createTask(taskDTO);
        return "redirect:/tasks?userId=" + userId;
    }
}