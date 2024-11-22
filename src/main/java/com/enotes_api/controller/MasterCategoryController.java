package com.enotes_api.controller;

import com.enotes_api.entity.MasterCategoryEntity;
import com.enotes_api.request.MasterCategoryRequest;
import com.enotes_api.service.MasterCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/master-category")
public class MasterCategoryController {
	
	@Autowired
	private MasterCategoryService masterCategoryService;
	
	@PostMapping
	public MasterCategoryEntity saveMasterCategory(@RequestBody MasterCategoryRequest masterCategoryRequest) {
		MasterCategoryEntity savedMasterCategory = masterCategoryService.saveMasterCategory(masterCategoryRequest);
		return savedMasterCategory;
	}
	
	@GetMapping
	public List<MasterCategoryEntity> getAllMasterCategories() {
		List<MasterCategoryEntity> masterCategories = masterCategoryService.getAllMasterCategories();
		return masterCategories;
	}

}
