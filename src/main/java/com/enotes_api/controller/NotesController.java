package com.enotes_api.controller;

import com.enotes_api.entity.NotesEntity;
import com.enotes_api.exception.FileUploadFailedException;
import com.enotes_api.exception.InvalidFileException;
import com.enotes_api.exception.ResourceNotFoundException;
import com.enotes_api.request.NotesRequest;
import com.enotes_api.response.NotesResponse;
import com.enotes_api.response.ResponseUtils;
import com.enotes_api.service.NotesService;
import com.enotes_api.utility.MapperUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
        NotesEntity notesEntity = notesService.getNotesById(notesId);
        NotesResponse notesResponse = mapperUtil.map(notesEntity, NotesResponse.class);
        return ResponseUtils.createSuccessResponse(notesResponse, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getAllNotes() {
        List<NotesResponse> allNotes = notesService.getAllNotes();
        return ResponseUtils.createSuccessResponse(allNotes, HttpStatus.OK);
    }

}
