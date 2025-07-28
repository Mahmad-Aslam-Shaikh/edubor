package com.enotes_api.repository;

import com.enotes_api.entity.UserAccountVerificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAccountVerificationRepository extends JpaRepository<UserAccountVerificationEntity, Integer> {
}
