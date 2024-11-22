package com.enotes_api.repository;

import com.enotes_api.entity.MasterCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MasterCategoryRepository extends JpaRepository<MasterCategoryEntity, Integer> {

}
