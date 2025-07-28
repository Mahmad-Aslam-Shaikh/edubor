package com.enotes_api.service;

import com.enotes_api.entity.FavoriteNotesEntity;
import com.enotes_api.exception.ResourceNotFoundException;

import java.util.List;

public interface FavoriteNotesService {

    void makeNoteAsFavorite(Integer notesId) throws ResourceNotFoundException;

    void removeNoteFromFavorite(Integer notesId) throws ResourceNotFoundException;

    FavoriteNotesEntity getFavoriteNoteById(Integer notesId, Integer userId) throws ResourceNotFoundException;

    List<FavoriteNotesEntity> getFavoriteNotesByUser();
}
