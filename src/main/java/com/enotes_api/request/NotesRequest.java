package com.enotes_api.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
public class NotesRequest {

    private String title;

    private String description;

    private MasterCategoryRequest category;

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class MasterCategoryRequest {

        private Integer id;

    }


}
