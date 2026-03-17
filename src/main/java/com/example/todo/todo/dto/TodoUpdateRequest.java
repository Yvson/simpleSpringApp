package com.example.todo.todo.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
public class TodoUpdateRequest {

    private String title;

    private String description;

    private Boolean completed;

    private LocalDate dueDate;

    private Set<String> tagNames = new HashSet<>();
}