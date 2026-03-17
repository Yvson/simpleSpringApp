package com.example.todo.todo.service;

import com.example.todo.todo.dto.TodoCreateRequest;
import com.example.todo.todo.dto.TodoResponse;
import com.example.todo.todo.dto.TodoUpdateRequest;
import com.example.todo.todo.exception.TodoNotFoundException;
import com.example.todo.tag.model.Tag;
import com.example.todo.tag.repository.TagRepository;
import com.example.todo.todo.model.Todo;
import com.example.todo.todo.repository.TodoRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TodoService {

    private final TodoRepository todoRepository;
    private final TagRepository tagRepository;

    public TodoService(TodoRepository todoRepository, TagRepository tagRepository) {
        this.todoRepository = todoRepository;
        this.tagRepository = tagRepository;
    }

    public List<TodoResponse> getAllTodos() {
        return todoRepository.findAllWithTags().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public TodoResponse getTodoById(Long id) {
        return todoRepository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new TodoNotFoundException(id));
    }

    public TodoResponse createTodo(TodoCreateRequest request) {
        Set<Tag> tags = request.getTagNames().stream()
                .map(name -> tagRepository.findByName(name)
                        .orElseGet(() -> tagRepository.save(Tag.builder().name(name).build())))
                .collect(Collectors.toSet());

        Todo todo = Todo.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .completed(request.isCompleted())
                .dueDate(request.getDueDate())
                .tags(tags)
                .build();

        return toResponse(todoRepository.save(todo));
    }

    public TodoResponse updateTodo(Long id, TodoUpdateRequest request) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new TodoNotFoundException(id));

        if (request.getTitle() != null) {
            todo.setTitle(request.getTitle());
        }
        if (request.getDescription() != null) {
            todo.setDescription(request.getDescription());
        }
        if (request.getCompleted() != null) {
            todo.setCompleted(request.getCompleted());
        }
        if (request.getDueDate() != null) {
            todo.setDueDate(request.getDueDate());
        }
        if (request.getTagNames() != null) {
            Set<Tag> tags = request.getTagNames().stream()
                    .map(name -> tagRepository.findByName(name)
                            .orElseGet(() -> tagRepository.save(Tag.builder().name(name).build())))
                    .collect(Collectors.toSet());
            todo.setTags(tags);
        }

        return toResponse(todoRepository.save(todo));
    }

    public void deleteTodo(Long id) {
        if (!todoRepository.existsById(id)) {
            throw new TodoNotFoundException(id);
        }
        todoRepository.deleteById(id);
    }

    private TodoResponse toResponse(Todo todo) {
        Set<String> tagNames = todo.getTags().stream()
                .map(Tag::getName)
                .collect(Collectors.toSet());
        return new TodoResponse(
                todo.getId(),
                todo.getTitle(),
                todo.getDescription(),
                todo.isCompleted(),
                todo.getDueDate(),
                tagNames
        );
    }
}