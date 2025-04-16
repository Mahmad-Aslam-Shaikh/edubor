package com.enotes_api.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Data
public class NotesResponse {

    private Integer id;

    private String title;

    private String description;

    private MasterCategoryResponse category;

    private List<FileResponse> files;

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class MasterCategoryResponse {

        private String name;

    }

}
