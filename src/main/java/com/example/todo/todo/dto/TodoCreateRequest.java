package com.example.todo.todo.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
public class TodoCreateRequest {

    @NotBlank(message = "Title is required")
    private String title;

    private String description;

    private boolean completed = false;

    private LocalDate dueDate;

    private Set<String> tagNames = new HashSet<>();
}