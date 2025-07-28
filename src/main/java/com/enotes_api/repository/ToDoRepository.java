package com.enotes_api.repository;

import com.enotes_api.entity.ToDoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ToDoRepository extends JpaRepository<ToDoEntity, Integer> {

    List<ToDoEntity> findByCreatedBy(Integer userId);

    List<ToDoEntity> findByCreatedByAndStatusIdIn(Integer userId, List<Integer> statusIds);

}
