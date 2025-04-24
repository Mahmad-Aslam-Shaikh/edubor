package com.enotes_api.service.implementation;

import com.enotes_api.entity.ToDoEntity;
import com.enotes_api.enums.ToDoStatusEnum;
import com.enotes_api.exception.ExceptionMessages;
import com.enotes_api.exception.ResourceNotFoundException;
import com.enotes_api.repository.ToDoRepository;
import com.enotes_api.request.ToDoRequest;
import com.enotes_api.response.ToDoResponse;
import com.enotes_api.response.ToDoStatusEnumResponse;
import com.enotes_api.service.ToDoService;
import com.enotes_api.utility.MapperUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Arrays;
import java.util.List;

@Service
@AllArgsConstructor
public class ToDoServiceImpl implements ToDoService {

    private ToDoRepository toDoRepository;

    private MapperUtil mapperUtil;

    @Override
    public ToDoResponse saveTodo(ToDoRequest toDoRequest) throws ResourceNotFoundException {
        // Validating the ToDoStatusEnums
        ToDoStatusEnum.fromId(toDoRequest.getStatusId());

        ToDoEntity toDoEntity = mapperUtil.map(toDoRequest, ToDoEntity.class);
        ToDoEntity savedToDoEntity = toDoRepository.save(toDoEntity);
        return mapperUtil.map(savedToDoEntity, ToDoResponse.class);
    }

    @Override
    public ToDoEntity getToDoById(Integer toDoId) throws ResourceNotFoundException {
        return toDoRepository.findById(toDoId)
                .orElseThrow(() -> new ResourceNotFoundException(ExceptionMessages.TODO_NOT_FOUND_MESSAGE + toDoId));
    }

    @Override
    public ToDoResponse updateTodo(Integer toDoId, ToDoRequest toDoRequest) throws ResourceNotFoundException {
        ToDoEntity toDoToBeUpdated = getToDoById(toDoId);

        if (!ObjectUtils.isEmpty(toDoRequest.getTitle()))
            toDoToBeUpdated.setTitle(toDoRequest.getTitle());

        if (!ObjectUtils.isEmpty(toDoRequest.getStatusId())) {
            // Validating the ToDoStatusEnums
            ToDoStatusEnum.fromId(toDoRequest.getStatusId());
            toDoToBeUpdated.setStatusId(toDoRequest.getStatusId());
        }

        ToDoEntity updatedToDo = toDoRepository.save(toDoToBeUpdated);
        return mapperUtil.map(updatedToDo, ToDoResponse.class);
    }

    @Override
    public boolean deleteToDoById(Integer toDoId) {
        ToDoEntity toDoToBeDeleted = null;
        try {
            toDoToBeDeleted = getToDoById(toDoId);
        } catch (ResourceNotFoundException e) {
            return Boolean.FALSE;
        }
        toDoRepository.delete(toDoToBeDeleted);
        return Boolean.TRUE;
    }

    @Override
    public List<ToDoEntity> getUsersTodo(Integer userId, List<Integer> statusIds) {
        if (statusIds == null)
            return toDoRepository.findByCreatedBy(userId);
        return toDoRepository.findByCreatedByAndStatusIdIn(userId, statusIds);
    }

    @Override
    public List<ToDoStatusEnumResponse> getAvailableTodoStatuses() {
        ToDoStatusEnum[] toDoStatuses = ToDoStatusEnum.values();
        List<ToDoStatusEnum> toDoStatusesList = Arrays.asList(toDoStatuses);
        return mapperUtil.mapList(toDoStatusesList, ToDoStatusEnumResponse.class);
    }
}
