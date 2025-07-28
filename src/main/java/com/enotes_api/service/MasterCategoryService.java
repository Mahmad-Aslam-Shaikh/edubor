package com.enotes_api.service;

import com.enotes_api.entity.MasterCategoryEntity;
import com.enotes_api.exception.ResourceAlreadyExistsException;
import com.enotes_api.exception.ResourceNotFoundException;
import com.enotes_api.request.MasterCategoryRequest;
import com.enotes_api.response.MasterCategoryResponse;

import java.util.List;

public interface MasterCategoryService {

    MasterCategoryResponse saveMasterCategory(MasterCategoryRequest masterCategoryRequest) throws ResourceAlreadyExistsException;

    List<MasterCategoryResponse> getAllMasterCategories();

    List<MasterCategoryResponse> getActiveMasterCategories();

    MasterCategoryEntity getMasterCategoryById(Integer categoryId) throws ResourceNotFoundException;

    Boolean deleteMasterCategoryById(Integer categoryId);

    MasterCategoryResponse updateMasterCategory(MasterCategoryRequest masterCategoryRequest) throws ResourceNotFoundException, ResourceAlreadyExistsException;

}
