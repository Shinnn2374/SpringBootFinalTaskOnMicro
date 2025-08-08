package com.example.user_service.controller;

import com.example.user_service.dto.UserDto;
import com.example.user_service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class WebUserController {
    private final UserService userService;

    @GetMapping("/users")
    public String getAllUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "users";
    }

    @GetMapping("/users/edit/{id}")
    public String editUser(@PathVariable Long id, Model model) {
        model.addAttribute("user", userService.getUserById(id));
        return "editUser";
    }

    @GetMapping("/users/new")
    public String showCreateForm(Model model) {
        model.addAttribute("user", new UserDto());
        return "createUser";
    }

    @PostMapping("/users")
    public String createUser(@ModelAttribute UserDto userDTO) {
        userService.createUser(userDTO);
        return "redirect:/users";
    }
}