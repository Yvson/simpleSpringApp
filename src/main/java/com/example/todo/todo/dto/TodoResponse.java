package com.example.todo.todo.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
public class TodoResponse {

    private Long id;
    private String title;
    private String description;
    private boolean completed;
    private LocalDate dueDate;
    private Set<String> tagNames = new HashSet<>();

    public TodoResponse(Long id, String title, String description, boolean completed, LocalDate dueDate, Set<String> tagNames) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.completed = completed;
        this.dueDate = dueDate;
        this.tagNames = tagNames != null ? tagNames : new HashSet<>();
    }
}