package com.enotes_api.endpoint;

import com.enotes_api.exception.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/v1/notes")
public interface FavoriteNotesEndpoint {

    @PostMapping("/favorite/{notes-id}")
    @PreAuthorize("hasRole('USER')")
    ResponseEntity<?> addToFavorite(@PathVariable(name = "notes-id") Integer notesId) throws ResourceNotFoundException;

    @GetMapping("/favorite/all")
    @PreAuthorize("hasRole('USER')")
    Object getUsersFavoriteNotes();

    @DeleteMapping("/favorite/{notes-id}")
    @PreAuthorize("hasRole('USER')")
    ResponseEntity<?> removeFromFavorite(@PathVariable(name = "notes-id") Integer notesId) throws ResourceNotFoundException;

}
