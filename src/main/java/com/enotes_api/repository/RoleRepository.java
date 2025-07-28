package com.enotes_api.repository;

import com.enotes_api.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<RoleEntity, Integer> {

    RoleEntity findByNameIgnoreCase(String roleName);
}
