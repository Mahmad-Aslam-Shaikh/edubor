package com.enotes_api.controller;

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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/notes")
@AllArgsConstructor
public class NotesController {

    private NotesService notesService;

    private MapperUtil mapperUtil;

    @PostMapping
    public ResponseEntity<?> saveNotes(@RequestPart("note") NotesRequest notesRequest,
                                       @RequestPart(name = "files", required = false) List<MultipartFile> files)
            throws ResourceNotFoundException, FileUploadFailedException, IOException, InvalidFileException {
        NotesResponse savedNotesResponse = notesService.saveNotes(notesRequest, files);
        return ResponseUtils.createSuccessResponse(savedNotesResponse, HttpStatus.CREATED);
    }

    @GetMapping("/{notes-id}")
    public ResponseEntity<?> getNotesById(@PathVariable(name = "notes-id") Integer notesId) throws ResourceNotFoundException {
        NotesEntity notesEntity = notesService.getNotesById(notesId, Boolean.FALSE);
        NotesResponse notesResponse = mapperUtil.map(notesEntity, NotesResponse.class);
        return ResponseUtils.createSuccessResponse(notesResponse, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getAllNotes() {
        List<NotesResponse> allNotes = notesService.getAllNotes();
        return ResponseUtils.createSuccessResponse(allNotes, HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<?> getUserNotesWithPagination(Integer userId, @RequestParam(name = "pageNo", defaultValue =
            "0") Integer pageNo) {
        NotesPaginationResponse userNotesWithPagination = notesService.getUserNotesWithPagination(1, pageNo);
        return ResponseUtils.createSuccessResponse(userNotesWithPagination, HttpStatus.OK);
    }

    @PutMapping("/{notes-id}")
    public ResponseEntity<?> updateNotes(@PathVariable(name = "notes-id") Integer notesId,
                                         @RequestPart(name = "note", required = false) NotesRequest notesRequest,
                                         @RequestPart(name = "files", required = false) List<MultipartFile> files)
            throws ResourceNotFoundException, InvalidFileException, FileUploadFailedException, IOException {
        NotesResponse updatedNotesResponse = notesService.updateNotes(notesId, notesRequest, files);
        return ResponseUtils.createSuccessResponse(updatedNotesResponse, HttpStatus.OK);
    }

    @PutMapping("/delete/{notes-id}")
    public ResponseEntity<?> deleteNotes(@PathVariable(name = "notes-id") Integer notesId)
            throws ResourceNotFoundException, InvalidFileException, FileUploadFailedException, IOException {
        NotesResponse deletedNote = notesService.deleteNotes(notesId);
        return ResponseUtils.createSuccessResponse(deletedNote, HttpStatus.OK);
    }

    @PutMapping("/restore/{notes-id}")
    public ResponseEntity<?> restoreNotes(@PathVariable(name = "notes-id") Integer notesId)
            throws ResourceNotFoundException, InvalidFileException, FileUploadFailedException, IOException {
        NotesResponse deletedNote = notesService.restoreNotes(notesId);
        return ResponseUtils.createSuccessResponse(deletedNote, HttpStatus.OK);
    }

    @GetMapping("/user/bin")
    public ResponseEntity<?> getUserNotesInRecycleBin(Integer userId) {
        List<NotesEntity> userNotesInRecycleBin = notesService.getUserNotesInRecycleBin(1);
        List<NotesResponse> userNotesInBinResponse = mapperUtil.mapList(userNotesInRecycleBin, NotesResponse.class);
        if (CollectionUtils.isEmpty(userNotesInBinResponse))
            return ResponseUtils.createSuccessResponseWithMessage(HttpStatus.OK, "Recycle bin is empty");
        return ResponseUtils.createSuccessResponse(userNotesInBinResponse, HttpStatus.OK);
    }

}
