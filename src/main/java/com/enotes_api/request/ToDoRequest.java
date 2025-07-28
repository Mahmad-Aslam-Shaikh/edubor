package com.enotes_api.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ToDoRequest {

    @NotBlank(message = "Title is required")
    private String title;

    private Integer statusId;

}
