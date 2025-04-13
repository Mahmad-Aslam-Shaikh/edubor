package com.enotes_api.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
public class NotesResponse {

    private Integer id;

    private String title;

    private String description;

    private MasterCategoryResponse category;

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class MasterCategoryResponse {

        private String name;

    }

}
