package com.enotes_api.service;

import com.enotes_api.entity.ToDoEntity;
import com.enotes_api.exception.ResourceNotFoundException;
import com.enotes_api.request.ToDoRequest;
import com.enotes_api.response.ToDoResponse;
import com.enotes_api.response.ToDoStatusEnumResponse;

import java.util.List;

public interface ToDoService {

    ToDoResponse saveTodo(ToDoRequest toDoRequest) throws ResourceNotFoundException;

    ToDoEntity getToDoById(Integer toDoId) throws ResourceNotFoundException;

    ToDoResponse updateTodo(Integer toDoId, ToDoRequest toDoRequest) throws ResourceNotFoundException;

    boolean deleteToDoById(Integer toDoId);

    List<ToDoEntity> getUsersTodo(Integer userId, List<Integer> statusIds);

    List<ToDoStatusEnumResponse> getAvailableTodoStatuses();
}
