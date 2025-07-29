package com.enotes_api.endpoint;

import com.enotes_api.exception.ResourceNotFoundException;
import com.enotes_api.request.ToDoRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequestMapping("/api/v1/todo")
public interface ToDoEndpoint {

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    ResponseEntity<?> saveTodo(@Valid @RequestBody ToDoRequest toDoRequest) throws ResourceNotFoundException;

    @GetMapping("/{todo-id}")
    @PreAuthorize("hasRole('USER')")
    ResponseEntity<?> getToDoById(@PathVariable(name = "todo-id") Integer toDoId) throws ResourceNotFoundException;

    @PutMapping("/{todo-id}")
    @PreAuthorize("hasRole('USER')")
    ResponseEntity<?> updateTodo(@PathVariable(name = "todo-id") Integer toDoId,
                                 @RequestBody ToDoRequest toDoRequest) throws ResourceNotFoundException;

    @DeleteMapping("/{todo-id}")
    @PreAuthorize("hasRole('USER')")
    ResponseEntity<?> deleteToDoById(@PathVariable(name = "todo-id") Integer toDoId);

    @GetMapping("/user")
    @PreAuthorize("hasRole('USER')")
    ResponseEntity<?> getUsersTodo(@RequestParam(name = "statusId", required = false) List<Integer> statusIds);

    @GetMapping("/status")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    ResponseEntity<?> getAllToDoStatuses();

}
