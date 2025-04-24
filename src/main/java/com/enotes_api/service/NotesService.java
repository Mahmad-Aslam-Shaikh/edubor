package com.enotes_api.service;

import com.enotes_api.entity.NotesEntity;
import com.enotes_api.exception.FileUploadFailedException;
import com.enotes_api.exception.InvalidFileException;
import com.enotes_api.exception.ResourceNotFoundException;
import com.enotes_api.request.NotesRequest;
import com.enotes_api.response.NotesPaginationResponse;
import com.enotes_api.response.NotesResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

public interface NotesService {

    NotesResponse saveNotes(NotesRequest notesRequest, List<MultipartFile> files) throws ResourceNotFoundException,
            FileUploadFailedException, IOException, InvalidFileException;

    NotesEntity getNotesById(Integer notesId, Boolean isDeleted) throws ResourceNotFoundException;

    List<NotesResponse> getAllNotes();

    NotesPaginationResponse getUserNotesWithPagination(Integer userId, Integer pageNo);

    NotesResponse updateNotes(Integer notesId, NotesRequest notesRequest, List<MultipartFile> files) throws ResourceNotFoundException, InvalidFileException, FileUploadFailedException, IOException;

    NotesResponse deleteNotes(Integer notesId) throws ResourceNotFoundException;

    NotesResponse restoreNotes(Integer notesId) throws ResourceNotFoundException;

    List<NotesEntity> getUserNotesInRecycleBin(Integer userId);

    void deleteOutDatedNotesFromBin(LocalDateTime previousDateTime);

    boolean deleteNotesFromRecycleBin(Integer notesId) throws ResourceNotFoundException;

    boolean emptyUserRecycleBin(Integer userId);

    NotesResponse copyNotes(Integer notesId) throws ResourceNotFoundException, InvalidFileException, FileUploadFailedException, IOException;
}
