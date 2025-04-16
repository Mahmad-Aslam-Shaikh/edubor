package com.enotes_api.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class NotesPaginationResponse {

    private List<NotesResponse> notes;

    private Integer pageNo;
    private Integer pageSize;
    private Long totalElements;
    private Integer totalPages;
    private Boolean isPageEmpty;
    private Boolean isFirstPage;
    private Boolean isLastPage;

}
