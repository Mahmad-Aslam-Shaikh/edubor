package com.enotes_api.response;

import com.enotes_api.enums.ToDoStatusEnum;
import com.enotes_api.exception.ResourceNotFoundException;
import lombok.Data;

@Data
public class ToDoResponse {

    private Integer id;

    private String title;

    private Integer statusId;

    public String getStatus() throws ResourceNotFoundException {
        return ToDoStatusEnum.fromId(statusId).name();
    }

}
