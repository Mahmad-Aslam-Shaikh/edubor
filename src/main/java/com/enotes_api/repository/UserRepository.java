package com.enotes_api.repository;

import com.enotes_api.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    boolean existsByEmail(String email);

    boolean existsByMobileNo(String mobileNo);

    Optional<UserEntity> findByIdAndIsActive(Integer id, Boolean isActive);

    Optional<UserEntity> findByEmail(String email);

}
