package com.enotes_api.service;

import com.enotes_api.entity.MasterCategoryEntity;
import com.enotes_api.request.MasterCategoryRequest;

import java.util.List;

public interface MasterCategoryService {
	
	MasterCategoryEntity saveMasterCategory(MasterCategoryRequest masterCategoryRequest);
	
	List<MasterCategoryEntity> getAllMasterCategories();
}
