package com.enotes_api.endpoint;

import com.enotes_api.exception.FileUploadFailedException;
import com.enotes_api.exception.InvalidFileException;
import com.enotes_api.exception.ResourceNotFoundException;
import com.enotes_api.request.NotesRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RequestMapping("/api/v1/notes")
public interface NotesEndpoint {

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    ResponseEntity<?> saveNotes(@RequestPart("note") NotesRequest notesRequest,
                                @RequestPart(name = "files", required = false) List<MultipartFile> files)
            throws ResourceNotFoundException, FileUploadFailedException, IOException, InvalidFileException;

    @GetMapping("/{notes-id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    ResponseEntity<?> getNotesById(@PathVariable(name = "notes-id") Integer notesId) throws ResourceNotFoundException;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<?> getAllNotes();

    @GetMapping("/user")
    @PreAuthorize("hasRole('USER')")
    ResponseEntity<?> getUserNotesWithPagination(@RequestParam(name = "pageNo", defaultValue =
            "0") Integer pageNo);

    @PutMapping("/{notes-id}")
    @PreAuthorize("hasRole('USER')")
    ResponseEntity<?> updateNotes(@PathVariable(name = "notes-id") Integer notesId,
                                  @RequestPart(name = "note", required = false) NotesRequest notesRequest,
                                  @RequestPart(name = "files", required = false) List<MultipartFile> files)
            throws ResourceNotFoundException, InvalidFileException, FileUploadFailedException, IOException;

    @PutMapping("/delete/{notes-id}")
    @PreAuthorize("hasRole('USER')")
    ResponseEntity<?> deleteNotes(@PathVariable(name = "notes-id") Integer notesId)
            throws ResourceNotFoundException, InvalidFileException, FileUploadFailedException, IOException;

    @PutMapping("/restore/{notes-id}")
    @PreAuthorize("hasRole('USER')")
    ResponseEntity<?> restoreNotes(@PathVariable(name = "notes-id") Integer notesId)
            throws ResourceNotFoundException;

    @GetMapping("/user/bin")
    @PreAuthorize("hasRole('USER')")
    ResponseEntity<?> getUserNotesInRecycleBin();

    @DeleteMapping("/bin/{notes-id}")
    @PreAuthorize("hasRole('USER')")
    ResponseEntity<?> deleteNoteFromRecycleBin(@PathVariable(name = "notes-id") Integer notesId) throws ResourceNotFoundException;

    @DeleteMapping("/bin/empty")
    @PreAuthorize("hasRole('USER')")
    ResponseEntity<?> emptyNotesRecycleBin();

    @PostMapping("/copy/{notes-id}")
    @PreAuthorize("hasRole('USER')")
    ResponseEntity<?> copyNotes(@PathVariable(name = "notes-id") Integer notesId)
            throws ResourceNotFoundException, FileUploadFailedException, IOException, InvalidFileException;

    @GetMapping("/search")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    ResponseEntity<?> searchUserNotesWithPagination(
            @RequestParam(name = "pageNo", defaultValue = "0") Integer pageNo, @RequestParam String keyword);

}
