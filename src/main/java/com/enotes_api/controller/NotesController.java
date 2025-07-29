package com.enotes_api.controller;

import com.enotes_api.endpoint.NotesEndpoint;
import com.enotes_api.entity.NotesEntity;
import com.enotes_api.exception.FileUploadFailedException;
import com.enotes_api.exception.InvalidFileException;
import com.enotes_api.exception.ResourceNotFoundException;
import com.enotes_api.request.NotesRequest;
import com.enotes_api.response.NotesPaginationResponse;
import com.enotes_api.response.NotesResponse;
import com.enotes_api.response.ResponseUtils;
import com.enotes_api.service.NotesService;
import com.enotes_api.utility.MapperUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@AllArgsConstructor
public class NotesController implements NotesEndpoint {

    private NotesService notesService;

    private MapperUtil mapperUtil;

    @Override
    public ResponseEntity<?> saveNotes(@RequestPart("note") NotesRequest notesRequest,
                                       @RequestPart(name = "files", required = false) List<MultipartFile> files)
            throws ResourceNotFoundException, FileUploadFailedException, IOException, InvalidFileException {
        NotesResponse savedNotesResponse = notesService.saveNotes(notesRequest, files);
        return ResponseUtils.createSuccessResponse(savedNotesResponse, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<?> getNotesById(@PathVariable(name = "notes-id") Integer notesId) throws ResourceNotFoundException {
        NotesEntity notesEntity = notesService.getNotesById(notesId, Boolean.FALSE);
        NotesResponse notesResponse = mapperUtil.map(notesEntity, NotesResponse.class);
        return ResponseUtils.createSuccessResponse(notesResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getAllNotes() {
        List<NotesResponse> allNotes = notesService.getAllNotes();
        return ResponseUtils.createSuccessResponse(allNotes, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getUserNotesWithPagination(@RequestParam(name = "pageNo", defaultValue =
            "0") Integer pageNo) {
        NotesPaginationResponse userNotesWithPagination = notesService.getUserNotesWithPagination(pageNo);
        return ResponseUtils.createSuccessResponse(userNotesWithPagination, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> updateNotes(@PathVariable(name = "notes-id") Integer notesId,
                                         @RequestPart(name = "note", required = false) NotesRequest notesRequest,
                                         @RequestPart(name = "files", required = false) List<MultipartFile> files)
            throws ResourceNotFoundException, InvalidFileException, FileUploadFailedException, IOException {
        NotesResponse updatedNotesResponse = notesService.updateNotes(notesId, notesRequest, files);
        return ResponseUtils.createSuccessResponse(updatedNotesResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> deleteNotes(@PathVariable(name = "notes-id") Integer notesId)
            throws ResourceNotFoundException, InvalidFileException, FileUploadFailedException, IOException {
        NotesResponse deletedNote = notesService.deleteNotes(notesId);
        return ResponseUtils.createSuccessResponse(deletedNote, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> restoreNotes(@PathVariable(name = "notes-id") Integer notesId)
            throws ResourceNotFoundException {
        NotesResponse deletedNote = notesService.restoreNotes(notesId);
        return ResponseUtils.createSuccessResponse(deletedNote, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getUserNotesInRecycleBin() {
        List<NotesEntity> userNotesInRecycleBin = notesService.getUserNotesInRecycleBin();
        List<NotesResponse> userNotesInBinResponse = mapperUtil.mapList(userNotesInRecycleBin, NotesResponse.class);
        if (CollectionUtils.isEmpty(userNotesInBinResponse))
            return ResponseUtils.createSuccessResponseWithMessage(HttpStatus.OK, "Recycle bin is empty");
        return ResponseUtils.createSuccessResponse(userNotesInBinResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> deleteNoteFromRecycleBin(@PathVariable(name = "notes-id") Integer notesId) throws ResourceNotFoundException {
        boolean isNoteDeletedFromBin = notesService.deleteNotesFromRecycleBin(notesId);
        if (isNoteDeletedFromBin) {
            return ResponseUtils.createSuccessResponseWithMessage(HttpStatus.OK, "Note deleted permanently");
        }
        return ResponseUtils.createFailureResponseWithMessage(HttpStatus.CONFLICT, "First please move the notes to " +
                "Recycle Bin");
    }

    @Override
    public ResponseEntity<?> emptyNotesRecycleBin() {
        boolean isRecycleBinMadeEmpty = notesService.emptyUserRecycleBin();
        if (isRecycleBinMadeEmpty) {
            return ResponseUtils.createSuccessResponseWithMessage(HttpStatus.OK, "Recycle Bin Successfully Made Empty");
        }
        return ResponseUtils.createSuccessResponseWithMessage(HttpStatus.CONFLICT, "Recycle Bin is already Empty");
    }

    @Override
    public ResponseEntity<?> copyNotes(@PathVariable(name = "notes-id") Integer notesId)
            throws ResourceNotFoundException, FileUploadFailedException, IOException, InvalidFileException {
        NotesResponse notesResponse = notesService.copyNotes(notesId);
        return ResponseUtils.createSuccessResponse(notesResponse, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<?> searchUserNotesWithPagination(
            @RequestParam(name = "pageNo", defaultValue = "0") Integer pageNo, @RequestParam String keyword) {
        NotesPaginationResponse userNotesWithPagination = notesService.searchUserNotesWithPagination(keyword, pageNo);
        return ResponseUtils.createSuccessResponse(userNotesWithPagination, HttpStatus.OK);
    }

}
