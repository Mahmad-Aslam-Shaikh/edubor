package com.enotes_api.controller;

import com.enotes_api.exception.ResourceAlreadyExistsException;
import com.enotes_api.exception.ResourceNotFoundException;
import com.enotes_api.request.MasterCategoryRequest;
import com.enotes_api.response.MasterCategoryResponse;
import com.enotes_api.response.ResponseUtils;
import com.enotes_api.service.MasterCategoryService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/master-category")
@AllArgsConstructor
public class MasterCategoryController {

    private MasterCategoryService masterCategoryService;

    @PostMapping
    public ResponseEntity<?> saveMasterCategory(@Valid @RequestBody MasterCategoryRequest masterCategoryRequest) throws ResourceAlreadyExistsException {
        MasterCategoryResponse savedMasterCategoryResponse =
                masterCategoryService.saveMasterCategory(masterCategoryRequest);
        return ResponseUtils.createSuccessResponse(savedMasterCategoryResponse, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<?> getAllMasterCategories() {
        List<MasterCategoryResponse> masterCategories = masterCategoryService.getAllMasterCategories();
        return ResponseUtils.createSuccessResponse(masterCategories, HttpStatus.OK);
    }

    @GetMapping("/active")
    public ResponseEntity<?> getActiveMasterCategories() {
        List<MasterCategoryResponse> masterCategories = masterCategoryService.getActiveMasterCategories();
        return ResponseUtils.createSuccessResponse(masterCategories, HttpStatus.OK);
    }

    @GetMapping("/{category-id}")
    public ResponseEntity<?> getMasterCategory(@PathVariable(name = "category-id") Integer categoryId) throws ResourceNotFoundException {
        MasterCategoryResponse masterCategory = masterCategoryService.getMasterCategoryById(categoryId);
        if (ObjectUtils.isEmpty(masterCategory)) {
            return ResponseUtils.createFailureResponseWithMessage(HttpStatus.NOT_FOUND, "Category Not Found with id =" +
                    " " + categoryId);
        }
        return ResponseUtils.createSuccessResponse(masterCategory, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<?> updateMasterCategory(@RequestBody MasterCategoryRequest masterCategoryRequest) throws ResourceNotFoundException, ResourceAlreadyExistsException {
        MasterCategoryResponse updatedMasterCategoryResponse =
                masterCategoryService.updateMasterCategory(masterCategoryRequest);
        return ResponseUtils.createSuccessResponse(updatedMasterCategoryResponse, HttpStatus.OK);
    }

    @PutMapping("/{category-id}")
    public ResponseEntity<?> deleteMasterCategory(@PathVariable(name = "category-id") Integer categoryId) {
        Boolean isDeleted = masterCategoryService.deleteMasterCategoryById(categoryId);
        if (isDeleted) {
            return ResponseUtils.createSuccessResponseWithMessage(HttpStatus.OK, "Category deleted Successfully");
        }
        return ResponseUtils.createSuccessResponseWithMessage(HttpStatus.NOT_FOUND,
                "Category Not Found with id = " + categoryId);
    }

}
