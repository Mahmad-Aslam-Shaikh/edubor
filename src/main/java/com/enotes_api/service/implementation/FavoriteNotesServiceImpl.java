package com.enotes_api.service.implementation;

import com.enotes_api.entity.FavoriteNotesEntity;
import com.enotes_api.entity.FavoriteNotesPKs;
import com.enotes_api.entity.NotesEntity;
import com.enotes_api.messages.ExceptionMessages;
import com.enotes_api.exception.ResourceNotFoundException;
import com.enotes_api.repository.FavoriteNotesRepository;
import com.enotes_api.service.FavoriteNotesService;
import com.enotes_api.service.NotesService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class FavoriteNotesServiceImpl implements FavoriteNotesService {

    private FavoriteNotesRepository favoriteNotesRepository;

    private NotesService notesService;

    @Override
    public void makeNoteAsFavorite(Integer notesId, Integer userId) throws ResourceNotFoundException {
        NotesEntity note = notesService.getNotesById(notesId, Boolean.FALSE);

        FavoriteNotesPKs favoriteNoteId = new FavoriteNotesPKs(userId, note.getId());

        FavoriteNotesEntity favoriteNote = FavoriteNotesEntity.builder()
                .id(favoriteNoteId)
                .note(note)
                .build();

        favoriteNotesRepository.save(favoriteNote);
    }

    @Override
    public FavoriteNotesEntity getFavoriteNoteById(Integer notesId, Integer userId) throws ResourceNotFoundException {
        FavoriteNotesPKs favoriteNoteId = new FavoriteNotesPKs(userId, notesId);
        return favoriteNotesRepository.findById(favoriteNoteId)
                .orElseThrow(() -> new ResourceNotFoundException(ExceptionMessages.NOTES_NOT_FOUND_MESSAGE + notesId));
    }

    @Override
    public List<FavoriteNotesEntity> getFavoriteNotesByUser(Integer userId) {
        return favoriteNotesRepository.findByIdUserId(userId);
    }

    @Override
    public void removeNoteFromFavorite(Integer notesId, Integer userId) throws ResourceNotFoundException {
        FavoriteNotesEntity favoriteNote = getFavoriteNoteById(notesId, userId);
        favoriteNotesRepository.delete(favoriteNote);
    }

}
