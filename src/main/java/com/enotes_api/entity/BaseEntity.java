package com.enotes_api.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@MappedSuperclass
public class BaseEntity {
	
	@Column(name = "is_active", nullable = false)
	private Boolean isActive = true;
	
	@Column(name = "is_deleted", nullable = false)
	private Boolean isDeleted = false;
	
	@Column(name = "created_by")
	private Integer createdBy;
	
	@Column(name = "created_on", nullable = false, updatable = false)
	private LocalDateTime createdOn = LocalDateTime.now();
	
	@Column(name = "updated_by")
	private Integer updatedBy;
	
	@Column(name = "updated_on")
	private LocalDateTime updatedOn;
	
}
