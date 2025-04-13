package com.enotes_api.service;

import com.enotes_api.entity.NotesEntity;
import com.enotes_api.exception.ResourceNotFoundException;
import com.enotes_api.request.NotesRequest;
import com.enotes_api.response.NotesResponse;

import java.util.List;

public interface NotesService {

    NotesResponse saveNotes(NotesRequest notesRequest) throws ResourceNotFoundException;

    NotesEntity getNotesById(Integer notesId) throws ResourceNotFoundException;

    List<NotesResponse> getAllNotes();
}
