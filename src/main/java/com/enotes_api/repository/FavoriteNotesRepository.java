package com.enotes_api.repository;

import com.enotes_api.entity.FavoriteNotesEntity;
import com.enotes_api.entity.FavoriteNotesPKs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface FavoriteNotesRepository extends JpaRepository<FavoriteNotesEntity, FavoriteNotesPKs> {

    List<FavoriteNotesEntity> findByIdUserId(Integer userId);

    @Modifying
    @Transactional
    void deleteByIdNoteId(Integer noteId);

}
