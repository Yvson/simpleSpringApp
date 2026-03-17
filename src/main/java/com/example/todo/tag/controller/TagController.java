package com.example.todo.tag.controller;

import com.example.todo.tag.dto.TagCreateRequest;
import com.example.todo.tag.dto.TagResponse;
import com.example.todo.tag.dto.TagUpdateRequest;
import com.example.todo.tag.service.TagService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tags")
@Tag(name = "Tag Management", description = "APIs for managing tags")
public class TagController {

    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping
    @Operation(summary = "Get all tags", description = "Retrieve a list of all tags")
    public List<TagResponse> getAllTags() {
        return tagService.getAllTags();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get tag by ID", description = "Retrieve a specific tag by its ID")
    public TagResponse getTagById(@PathVariable Long id) {
        return tagService.getTagById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new tag", description = "Add a new tag")
    public TagResponse createTag(@Valid @RequestBody TagCreateRequest request) {
        return tagService.createTag(request);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing tag", description = "Modify an existing tag by its ID")
    public TagResponse updateTag(@PathVariable Long id, @Valid @RequestBody TagUpdateRequest request) {
        return tagService.updateTag(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a tag", description = "Remove a tag by its ID")
    public void deleteTag(@PathVariable Long id) {
        tagService.deleteTag(id);
    }
}
