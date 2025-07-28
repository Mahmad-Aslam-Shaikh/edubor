package com.enotes_api.enums;

import com.enotes_api.exception.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ToDoStatusEnum {

    NOT_STARTED(1),
    IN_PROGRESS(2),
    COMPLETED(3);

    private final int statusId;

    public static ToDoStatusEnum fromId(int id) throws ResourceNotFoundException {
        for (ToDoStatusEnum status : ToDoStatusEnum.values()) {
            if (status.getStatusId() == id) {
                return status;
            }
        }
        throw new ResourceNotFoundException("Invalid Todo status ID: " + id);
    }

}
