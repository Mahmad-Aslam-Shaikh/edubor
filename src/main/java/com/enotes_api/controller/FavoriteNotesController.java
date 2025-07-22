package com.enotes_api.controller;

import com.enotes_api.entity.FavoriteNotesEntity;
import com.enotes_api.exception.ResourceNotFoundException;
import com.enotes_api.response.NotesResponse;
import com.enotes_api.response.ResponseUtils;
import com.enotes_api.service.FavoriteNotesService;
import com.enotes_api.utility.MapperUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/notes")
@AllArgsConstructor
public class FavoriteNotesController {

    private FavoriteNotesService favoriteNotesService;

    private MapperUtil mapperUtil;

    @PostMapping("/favorite/{notes-id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> addToFavorite(@PathVariable(name = "notes-id") Integer notesId) throws ResourceNotFoundException {
        Integer userId = 1;
        favoriteNotesService.makeNoteAsFavorite(notesId, userId);
        return ResponseUtils.createSuccessResponseWithMessage(HttpStatus.CREATED, "Notes added to favorites");
    }

    @GetMapping("/favorite/all")
    @PreAuthorize("hasRole('USER')")
    public Object getUsersFavoriteNotes() {
        Integer userId = 1;
        List<FavoriteNotesEntity> userFavoriteNotes = favoriteNotesService.getFavoriteNotesByUser(userId);
        List<NotesResponse> favoriteNoteResponse =
                userFavoriteNotes.stream().map(eachFavNote ->
                        mapperUtil.map(eachFavNote.getNote(), NotesResponse.class)).collect(Collectors.toList());
        return ResponseUtils.createSuccessResponse(favoriteNoteResponse, HttpStatus.OK);
    }

    @DeleteMapping("/favorite/{notes-id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> removeFromFavorite(@PathVariable(name = "notes-id") Integer notesId) throws ResourceNotFoundException {
        Integer userId = 1;
        favoriteNotesService.removeNoteFromFavorite(notesId, userId);
        return ResponseUtils.createSuccessResponseWithMessage(HttpStatus.OK, "Notes removed from favorites");
    }


}
