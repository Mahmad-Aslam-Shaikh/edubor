package com.enotes_api.repository;

import com.enotes_api.entity.MasterCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MasterCategoryRepository extends JpaRepository<MasterCategoryEntity, Integer> {

    List<MasterCategoryEntity> findByIsActive(Boolean isActive);

    Optional<MasterCategoryEntity> findByIdAndIsDeleted(Integer categoryId, Boolean isDeleted);

    List<MasterCategoryEntity> findByIsActiveAndIsDeleted(Boolean isActive, Boolean isDeleted);

    List<MasterCategoryEntity> findAllByIsDeleted(Boolean isDeleted);

    Boolean existsByName(String categoryName);
}
