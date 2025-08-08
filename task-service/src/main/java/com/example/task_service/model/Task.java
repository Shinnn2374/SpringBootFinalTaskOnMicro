package com.example.task_service.model;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private boolean completed;
    private Long userId;
}