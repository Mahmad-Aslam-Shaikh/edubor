package com.enotes_api.repository;

import com.enotes_api.entity.NotesEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface NotesRepository extends JpaRepository<NotesEntity, Integer> {

    @Query("SELECT n FROM NOTES n WHERE n.id = :notesId AND (n.isDeleted = :isDeleted OR n.isDeleted IS NULL)")
    Optional<NotesEntity> findNonDeletedNoteById(@Param("notesId") Integer notesId,
                                                 @Param("isDeleted") Boolean isDeleted);

//    Optional<Object> findByIdAndIsDeleted(Integer notesId, Boolean isDeleted);

    @Query(
            value = "SELECT n FROM NOTES n WHERE n.createdBy = :userId AND (n.isDeleted = false OR n.isDeleted IS " +
                    "NULL)",
            countQuery = "SELECT COUNT(n) FROM NOTES n WHERE n.createdBy = :userId AND (n.isDeleted = false OR n" +
                    ".isDeleted IS NULL)"
    )
    Page<NotesEntity> getNonDeletedUserNotes(@Param("userId") Integer userId, Pageable pageable);

    List<NotesEntity> findByCreatedByAndIsDeleted(Integer userId, Boolean isDeleted);

    List<NotesEntity> findAllByIsDeletedTrueAndDeletedOnBefore(LocalDateTime previousDateTime);

    List<NotesEntity> findAllByIsDeletedTrueAndCreatedBy(Integer userId);

    @Query("""
                SELECT n
                FROM NOTES n
                JOIN n.category c
                WHERE n.isDeleted = false
                  AND n.createdBy = :userId
                  AND (
                       LOWER(n.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR
                       LOWER(n.description) LIKE LOWER(CONCAT('%', :keyword, '%')) OR
                       LOWER(c.name) LIKE LOWER(CONCAT('%', :keyword, '%'))
                  )
            """)
    Page<NotesEntity> searchNotesByKeywordWithPagination(@Param("keyword") String keyword,
                                                         @Param("userId") Integer userId,
                                                         Pageable pageable);

}
