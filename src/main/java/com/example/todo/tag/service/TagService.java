package com.example.todo.tag.service;

import com.example.todo.tag.dto.TagCreateRequest;
import com.example.todo.tag.dto.TagResponse;
import com.example.todo.tag.dto.TagUpdateRequest;
import com.example.todo.tag.model.Tag;
import com.example.todo.tag.repository.TagRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TagService {

    private final TagRepository tagRepository;

    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public List<TagResponse> getAllTags() {
        return tagRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public TagResponse getTagById(Long id) {
        Tag tag = tagRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tag not found with id: " + id));
        return toResponse(tag);
    }

    public TagResponse createTag(TagCreateRequest request) {
        if (tagRepository.findByName(request.getName()).isPresent()) {
            throw new RuntimeException("Tag with name '" + request.getName() + "' already exists");
        }
        Tag tag = Tag.builder()
                .name(request.getName())
                .build();
        return toResponse(tagRepository.save(tag));
    }

    public TagResponse updateTag(Long id, TagUpdateRequest request) {
        Tag tag = tagRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tag not found with id: " + id));

        if (request.getName() != null) {
            if (!request.getName().equals(tag.getName()) &&
                tagRepository.findByName(request.getName()).isPresent()) {
                throw new RuntimeException("Tag with name '" + request.getName() + "' already exists");
            }
            tag.setName(request.getName());
        }

        return toResponse(tagRepository.save(tag));
    }

    public void deleteTag(Long id) {
        if (!tagRepository.existsById(id)) {
            throw new RuntimeException("Tag not found with id: " + id);
        }
        tagRepository.deleteById(id);
    }

    private TagResponse toResponse(Tag tag) {
        return new TagResponse(tag.getId(), tag.getName());
    }
}
