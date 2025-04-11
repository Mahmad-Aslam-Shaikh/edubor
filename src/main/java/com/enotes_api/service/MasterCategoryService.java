package com.enotes_api.service;

import com.enotes_api.exception.ResourceNotFoundException;
import com.enotes_api.request.MasterCategoryRequest;
import com.enotes_api.response.MasterCategoryResponse;

import java.util.List;

public interface MasterCategoryService {

    MasterCategoryResponse saveMasterCategory(MasterCategoryRequest masterCategoryRequest);

    List<MasterCategoryResponse> getAllMasterCategories();

    List<MasterCategoryResponse> getActiveMasterCategories();

    MasterCategoryResponse getMasterCategoryById(Integer categoryId) throws ResourceNotFoundException;

    Boolean deleteMasterCategoryById(Integer categoryId);

    MasterCategoryResponse updateMasterCategory(MasterCategoryRequest masterCategoryRequest) throws ResourceNotFoundException;

}
