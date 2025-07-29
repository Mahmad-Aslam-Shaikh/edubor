package com.enotes_api.controller;

import com.enotes_api.endpoint.MasterCategoryEndpoint;
import com.enotes_api.entity.MasterCategoryEntity;
import com.enotes_api.exception.ResourceAlreadyExistsException;
import com.enotes_api.exception.ResourceNotFoundException;
import com.enotes_api.request.MasterCategoryRequest;
import com.enotes_api.response.MasterCategoryResponse;
import com.enotes_api.response.ResponseUtils;
import com.enotes_api.service.MasterCategoryService;
import com.enotes_api.utility.MapperUtil;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class MasterCategoryController implements MasterCategoryEndpoint {

    private MasterCategoryService masterCategoryService;

    private MapperUtil mapperUtil;

    @Override
    public ResponseEntity<?> saveMasterCategory(@Valid @RequestBody MasterCategoryRequest masterCategoryRequest) throws ResourceAlreadyExistsException {
        MasterCategoryResponse savedMasterCategoryResponse =
                masterCategoryService.saveMasterCategory(masterCategoryRequest);
        return ResponseUtils.createSuccessResponse(savedMasterCategoryResponse, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<?> getAllMasterCategories() {
        List<MasterCategoryResponse> masterCategories = masterCategoryService.getAllMasterCategories();
        return ResponseUtils.createSuccessResponse(masterCategories, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getActiveMasterCategories() {
        List<MasterCategoryResponse> masterCategories = masterCategoryService.getActiveMasterCategories();
        return ResponseUtils.createSuccessResponse(masterCategories, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getMasterCategory(@PathVariable(name = "category-id") Integer categoryId) throws ResourceNotFoundException {
        MasterCategoryEntity masterCategory = masterCategoryService.getMasterCategoryById(categoryId);
        MasterCategoryResponse masterCategoryResponse = mapperUtil.map(masterCategory, MasterCategoryResponse.class);
        if (ObjectUtils.isEmpty(masterCategoryResponse)) {
            return ResponseUtils.createFailureResponseWithMessage(HttpStatus.NOT_FOUND, "Category Not Found with id =" +
                    " " + categoryId);
        }
        return ResponseUtils.createSuccessResponse(masterCategoryResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> updateMasterCategory(@RequestBody MasterCategoryRequest masterCategoryRequest) throws ResourceNotFoundException, ResourceAlreadyExistsException {
        MasterCategoryResponse updatedMasterCategoryResponse =
                masterCategoryService.updateMasterCategory(masterCategoryRequest);
        return ResponseUtils.createSuccessResponse(updatedMasterCategoryResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> deleteMasterCategory(@PathVariable(name = "category-id") Integer categoryId) {
        Boolean isDeleted = masterCategoryService.deleteMasterCategoryById(categoryId);
        if (isDeleted) {
            return ResponseUtils.createSuccessResponseWithMessage(HttpStatus.OK, "Category deleted Successfully");
        }
        return ResponseUtils.createSuccessResponseWithMessage(HttpStatus.NOT_FOUND,
                "Category Not Found with id = " + categoryId);
    }

}
