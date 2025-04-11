package com.enotes_api.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Data
@MappedSuperclass
public class BaseEntity {

	@CreatedBy
	@Column(name = "created_by", updatable = false)
	private Integer createdBy;

	@CreatedDate
	@Column(name = "created_on", nullable = false, updatable = false)
	private LocalDateTime createdOn;

	@LastModifiedBy
	@Column(name = "updated_by", insertable = false)
	private Integer updatedBy;

	@LastModifiedDate
	@Column(name = "updated_on", insertable = false)
	private LocalDateTime updatedOn;
	
}
