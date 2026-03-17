package com.example.todo.tag.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TagCreateRequest {

    @NotBlank(message = "Tag name is required")
    private String name;
}
