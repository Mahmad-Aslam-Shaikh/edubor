package com.enotes_api.controller;

import com.enotes_api.endpoint.FavoriteNotesEndpoint;
import com.enotes_api.entity.FavoriteNotesEntity;
import com.enotes_api.exception.ResourceNotFoundException;
import com.enotes_api.response.NotesResponse;
import com.enotes_api.response.ResponseUtils;
import com.enotes_api.service.FavoriteNotesService;
import com.enotes_api.utility.MapperUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
@AllArgsConstructor
public class FavoriteNotesController implements FavoriteNotesEndpoint {

    private FavoriteNotesService favoriteNotesService;

    private MapperUtil mapperUtil;

    @Override
    public ResponseEntity<?> addToFavorite(@PathVariable(name = "notes-id") Integer notesId) throws ResourceNotFoundException {
        log.info("FavoriteNotesController : addToFavorite() : Started");
        favoriteNotesService.makeNoteAsFavorite(notesId);
        log.info("FavoriteNotesController : addToFavorite() : End");
        return ResponseUtils.createSuccessResponseWithMessage(HttpStatus.CREATED, "Notes added to favorites");
    }

    @Override
    public ResponseEntity<?> getUsersFavoriteNotes() {
        log.info("FavoriteNotesController : getUsersFavoriteNotes() : Started");
        List<FavoriteNotesEntity> userFavoriteNotes = favoriteNotesService.getFavoriteNotesByUser();
        List<NotesResponse> favoriteNoteResponse =
                userFavoriteNotes.stream().map(eachFavNote ->
                        mapperUtil.map(eachFavNote.getNote(), NotesResponse.class)).collect(Collectors.toList());
        log.info("FavoriteNotesController : getUsersFavoriteNotes() : End");
        return ResponseUtils.createSuccessResponse(favoriteNoteResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> removeFromFavorite(@PathVariable(name = "notes-id") Integer notesId) throws ResourceNotFoundException {
        log.info("FavoriteNotesController : removeFromFavorite() : Started");
        favoriteNotesService.removeNoteFromFavorite(notesId);
        log.info("FavoriteNotesController : removeFromFavorite() : End");
        return ResponseUtils.createSuccessResponseWithMessage(HttpStatus.OK, "Notes removed from favorites");
    }

}
