package com.enotes_api.controller;

import com.enotes_api.request.MasterCategoryRequest;
import com.enotes_api.response.MasterCategoryResponse;
import com.enotes_api.service.MasterCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<MasterCategoryResponse> saveMasterCategory(@RequestBody MasterCategoryRequest masterCategoryRequest) {
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


}
