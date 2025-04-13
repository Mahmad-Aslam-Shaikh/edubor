package com.enotes_api.repository;

import com.enotes_api.entity.NotesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotesRepository extends JpaRepository<NotesEntity, Integer> {
}
