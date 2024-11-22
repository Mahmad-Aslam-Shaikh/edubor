package com.enotes_api.service.implementation;

import com.enotes_api.entity.MasterCategoryEntity;
import com.enotes_api.repository.MasterCategoryRepository;
import com.enotes_api.request.MasterCategoryRequest;
import com.enotes_api.service.MasterCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MasterCategoryServiceImpl implements MasterCategoryService {
	
	@Autowired
	private MasterCategoryRepository masterCategoryRepository;
	
	@Override
	public MasterCategoryEntity saveMasterCategory(MasterCategoryRequest masterCategoryRequest) {
		MasterCategoryEntity masterCategoryEntity = new MasterCategoryEntity();
		masterCategoryEntity.setName(masterCategoryRequest.getName());
		masterCategoryEntity.setDescription(masterCategoryRequest.getDescription());
		MasterCategoryEntity savedMasterCategory = masterCategoryRepository.save(masterCategoryEntity);
		return savedMasterCategory;
	}
	
	@Override
	public List<MasterCategoryEntity> getAllMasterCategories() {
		return masterCategoryRepository.findAll();
	}
	
}
