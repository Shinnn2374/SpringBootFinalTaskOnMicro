package com.example.user_service.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 3, max = 50)
    @Column(unique = false)
    private String username;

    @NotBlank
    @Size(min = 3, max = 50)
    @Column(unique = false)
    private String email;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
