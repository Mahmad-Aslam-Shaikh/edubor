package com.enotes_api.service.implementation;

import com.enotes_api.entity.FavoriteNotesEntity;
import com.enotes_api.entity.FavoriteNotesPKs;
import com.enotes_api.entity.NotesEntity;
import com.enotes_api.exception.ResourceNotFoundException;
import com.enotes_api.messages.ExceptionMessages;
import com.enotes_api.repository.FavoriteNotesRepository;
import com.enotes_api.service.FavoriteNotesService;
import com.enotes_api.service.NotesService;
import com.enotes_api.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class FavoriteNotesServiceImpl implements FavoriteNotesService {

    private FavoriteNotesRepository favoriteNotesRepository;

    private NotesService notesService;

    private UserService userService;

    @Override
    public void makeNoteAsFavorite(Integer notesId) throws ResourceNotFoundException {
        log.info("FavoriteNotesServiceImpl : makeNoteAsFavorite() : Started");
        Integer userId = userService.getCurrentLoggedInUser().getId();
        NotesEntity note = notesService.getNotesById(notesId, Boolean.FALSE);

        FavoriteNotesPKs favoriteNoteId = new FavoriteNotesPKs(userId, note.getId());

        FavoriteNotesEntity favoriteNote = FavoriteNotesEntity.builder()
                .id(favoriteNoteId)
                .note(note)
                .build();

        favoriteNotesRepository.save(favoriteNote);
        log.info("FavoriteNotesServiceImpl : makeNoteAsFavorite() : End");
    }

    @Override
    public FavoriteNotesEntity getFavoriteNoteById(Integer notesId, Integer userId) throws ResourceNotFoundException {
        log.info("FavoriteNotesServiceImpl : getFavoriteNoteById() : Started");
        FavoriteNotesPKs favoriteNoteId = new FavoriteNotesPKs(userId, notesId);
        log.info("FavoriteNotesServiceImpl : getFavoriteNoteById() : End");
        return favoriteNotesRepository.findById(favoriteNoteId)
                .orElseThrow(() -> new ResourceNotFoundException(ExceptionMessages.NOTES_NOT_FOUND_MESSAGE + notesId));
    }

    @Override
    public List<FavoriteNotesEntity> getFavoriteNotesByUser() {
        log.info("FavoriteNotesServiceImpl : getFavoriteNotesByUser() : Started");
        Integer userId = userService.getCurrentLoggedInUser().getId();
        log.info("FavoriteNotesServiceImpl : getFavoriteNotesByUser() : End");
        return favoriteNotesRepository.findByIdUserId(userId);
    }

    @Override
    public void removeNoteFromFavorite(Integer notesId) throws ResourceNotFoundException {
        log.info("FavoriteNotesServiceImpl : removeNoteFromFavorite() : Started");
        Integer userId = userService.getCurrentLoggedInUser().getId();
        FavoriteNotesEntity favoriteNote = getFavoriteNoteById(notesId, userId);
        favoriteNotesRepository.delete(favoriteNote);
        log.info("FavoriteNotesServiceImpl : removeNoteFromFavorite() : End");
    }

}
