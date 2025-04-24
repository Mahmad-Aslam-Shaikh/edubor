package com.enotes_api.service;

import com.enotes_api.entity.FavoriteNotesEntity;
import com.enotes_api.exception.ResourceNotFoundException;

import java.util.List;

public interface FavoriteNotesService {

    void makeNoteAsFavorite(Integer notesId, Integer userId) throws ResourceNotFoundException;

    void removeNoteFromFavorite(Integer notesId, Integer userId) throws ResourceNotFoundException;

    FavoriteNotesEntity getFavoriteNoteById(Integer notesId, Integer userId) throws ResourceNotFoundException;

    List<FavoriteNotesEntity> getFavoriteNotesByUser(Integer userId);
}
