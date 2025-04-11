package com.enotes_api.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class MasterCategoryRequest {

	private Integer id;

	@NotBlank(message = "Name is required")
	private String name;

	@NotBlank(message = "Description is required")
	private String description;

}
