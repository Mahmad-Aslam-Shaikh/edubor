package com.enotes_api.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "master_categories")
@EntityListeners(AuditingEntityListener.class)
public class MasterCategoryEntity extends BaseEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "category_id")
	private Integer id;
	
	@Column(name = "name", length = 50, nullable = false, unique = true)
	private String name;
	
	@Column(name = "description", length = 250, nullable = false)
	private String description;

	@Column(name = "is_active", nullable = false)
	private Boolean isActive = true;

	@Column(name = "is_deleted", nullable = false)
	private Boolean isDeleted = false;


}
