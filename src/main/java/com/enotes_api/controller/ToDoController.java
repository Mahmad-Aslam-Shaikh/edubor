package com.enotes_api.controller;

import com.enotes_api.entity.ToDoEntity;
import com.enotes_api.exception.ResourceNotFoundException;
import com.enotes_api.request.ToDoRequest;
import com.enotes_api.response.ResponseUtils;
import com.enotes_api.response.ToDoResponse;
import com.enotes_api.response.ToDoStatusEnumResponse;
import com.enotes_api.service.ToDoService;
import com.enotes_api.utility.MapperUtil;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/todo")
@AllArgsConstructor
public class ToDoController {

    private ToDoService toDoService;

    private MapperUtil mapperUtil;

    @PostMapping
    public ResponseEntity<?> saveTodo(@Valid @RequestBody ToDoRequest toDoRequest) throws ResourceNotFoundException {
        ToDoResponse savedToDoResponse = toDoService.saveTodo(toDoRequest);
        return ResponseUtils.createSuccessResponse(savedToDoResponse, HttpStatus.CREATED);
    }

    @GetMapping("/{todo-id}")
    public ResponseEntity<?> getToDoById(@PathVariable(name = "todo-id") Integer toDoId) throws ResourceNotFoundException {
        ToDoEntity toDoEntity = toDoService.getToDoById(toDoId);
        ToDoResponse toDoResponse = mapperUtil.map(toDoEntity, ToDoResponse.class);
        return ResponseUtils.createSuccessResponse(toDoResponse, HttpStatus.OK);
    }

    @PutMapping("/{todo-id}")
    public ResponseEntity<?> updateTodo(@PathVariable(name = "todo-id") Integer toDoId,
                                        @RequestBody ToDoRequest toDoRequest) throws ResourceNotFoundException {
        ToDoResponse updatedToDoResponse = toDoService.updateTodo(toDoId, toDoRequest);
        return ResponseUtils.createSuccessResponse(updatedToDoResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{todo-id}")
    public ResponseEntity<?> deleteToDoById(@PathVariable(name = "todo-id") Integer toDoId) {
        boolean isToDoDeleted = toDoService.deleteToDoById(toDoId);
        if (isToDoDeleted) {
            return ResponseUtils.createSuccessResponseWithMessage(HttpStatus.OK, "Todo deleted permanently");
        }
        return ResponseUtils.createFailureResponseWithMessage(HttpStatus.NOT_FOUND, "Todo Not Found");
    }

    @GetMapping("/user")
    public ResponseEntity<?> getUsersTodo(@RequestParam(name = "statusId", required = false) List<Integer> statusIds) {
        Integer userId = 1;
        List<ToDoEntity> usersTodo = toDoService.getUsersTodo(userId, statusIds);
        List<ToDoResponse> userToDoResponse = mapperUtil.mapList(usersTodo, ToDoResponse.class);
        if (CollectionUtils.isEmpty(userToDoResponse))
            return ResponseUtils.createSuccessResponseWithMessage(HttpStatus.OK, "User does not have any Todo");
        return ResponseUtils.createSuccessResponse(userToDoResponse, HttpStatus.OK);
    }

    @GetMapping("/status")
    public ResponseEntity<?> getAllToDoStatuses() {
        List<ToDoStatusEnumResponse> availableTodoStatuses = toDoService.getAvailableTodoStatuses();
//        ToDoResponse toDoResponse = mapperUtil.map(toDoEntity, ToDoResponse.class);
        return ResponseUtils.createSuccessResponse(availableTodoStatuses, HttpStatus.OK);
    }


}
