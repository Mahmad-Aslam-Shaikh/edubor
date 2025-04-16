package com.enotes_api.repository;

import com.enotes_api.entity.NotesEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotesRepository extends JpaRepository<NotesEntity, Integer> {

    Page<NotesEntity> findByCreatedBy(Integer userId, Pageable pageable);

}
