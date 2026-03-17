package com.example.todo.todo.controller;

import com.example.todo.todo.dto.TodoCreateRequest;
import com.example.todo.todo.dto.TodoResponse;
import com.example.todo.todo.dto.TodoUpdateRequest;
import com.example.todo.todo.service.TodoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/todos")
@Tag(name = "Todo Management", description = "APIs for managing todos")
public class TodoController {

    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping
    @Operation(summary = "Get all todos", description = "Retrieve a list of all todos")
    public List<TodoResponse> getAllTodos() {
        return todoService.getAllTodos();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get todo by ID", description = "Retrieve a specific todo by its ID")
    public TodoResponse getTodoById(@PathVariable Long id) {
        return todoService.getTodoById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new todo", description = "Add a new todo to the list")
    public TodoResponse createTodo(@Valid @RequestBody TodoCreateRequest request) {
        return todoService.createTodo(request);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing todo", description = "Modify an existing todo by its ID")
    public TodoResponse updateTodo(@PathVariable Long id, @Valid @RequestBody TodoUpdateRequest request) {
        return todoService.updateTodo(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a todo", description = "Remove a todo from the list by its ID")
    public void deleteTodo(@PathVariable Long id) {
        todoService.deleteTodo(id);
    }
}