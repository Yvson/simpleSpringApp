package com.example.todo.todo.repository;

import com.example.todo.todo.model.Todo;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {

    @Query("SELECT t FROM Todo t LEFT JOIN FETCH t.tags")
    List<Todo> findAllWithTags();

    @EntityGraph(attributePaths = "tags")
    Optional<Todo> findById(Long id);
}