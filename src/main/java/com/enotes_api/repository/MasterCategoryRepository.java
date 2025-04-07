package com.enotes_api.repository;

import com.enotes_api.entity.MasterCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MasterCategoryRepository extends JpaRepository<MasterCategoryEntity, Integer> {

    List<MasterCategoryEntity> findByIsActive(Boolean isActive);
}
