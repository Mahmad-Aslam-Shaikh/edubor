package com.enotes_api.controller;

import com.enotes_api.exception.ResourceAlreadyExistsException;
import com.enotes_api.exception.ResourceNotFoundException;
import com.enotes_api.request.MasterCategoryRequest;
import com.enotes_api.response.MasterCategoryResponse;
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
    public ResponseEntity<MasterCategoryResponse> saveMasterCategory(@Valid @RequestBody MasterCategoryRequest masterCategoryRequest) throws ResourceAlreadyExistsException {
        MasterCategoryResponse savedMasterCategoryResponse =
                masterCategoryService.saveMasterCategory(masterCategoryRequest);
        return new ResponseEntity<>(savedMasterCategoryResponse, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<MasterCategoryResponse>> getAllMasterCategories() {
        List<MasterCategoryResponse> masterCategories = masterCategoryService.getAllMasterCategories();
        return new ResponseEntity<>(masterCategories, HttpStatus.OK);
    }

    @GetMapping("/active")
    public ResponseEntity<List<MasterCategoryResponse>> getActiveMasterCategories() {
        List<MasterCategoryResponse> masterCategories = masterCategoryService.getActiveMasterCategories();
        return new ResponseEntity<>(masterCategories, HttpStatus.OK);
    }

    @GetMapping("/{category-id}")
    public ResponseEntity<Object> getMasterCategory(@PathVariable(name = "category-id") Integer categoryId) throws ResourceNotFoundException {
        MasterCategoryResponse masterCategory = masterCategoryService.getMasterCategoryById(categoryId);
        if (ObjectUtils.isEmpty(masterCategory)) {
            return new ResponseEntity<>("Category Not Found with id = " + categoryId, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(masterCategory, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<MasterCategoryResponse> updateMasterCategory(@RequestBody MasterCategoryRequest masterCategoryRequest) throws ResourceNotFoundException, ResourceAlreadyExistsException {
        MasterCategoryResponse updatedMasterCategoryResponse =
                masterCategoryService.updateMasterCategory(masterCategoryRequest);
        return new ResponseEntity<>(updatedMasterCategoryResponse, HttpStatus.OK);
    }

    @PutMapping("/{category-id}")
    public ResponseEntity<String> deleteMasterCategory(@PathVariable(name = "category-id") Integer categoryId) {
        Boolean isDeleted = masterCategoryService.deleteMasterCategoryById(categoryId);
        if (isDeleted) {
            return new ResponseEntity<>("Category deleted Successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("Category Not Found with id = " + categoryId, HttpStatus.NOT_FOUND);
    }

}
