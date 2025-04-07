package com.enotes_api.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MasterCategoryResponse {

    private Integer id;

    private String name;

    private String description;

    private Boolean isActive;

    private Integer createdBy;

    private LocalDateTime createdOn;

}
