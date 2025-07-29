package com.enotes_api.endpoint;

import com.enotes_api.exception.ResourceAlreadyExistsException;
import com.enotes_api.exception.ResourceNotFoundException;
import com.enotes_api.request.MasterCategoryRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/v1/master-category")
public interface MasterCategoryEndpoint {

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<?> saveMasterCategory(@Valid @RequestBody MasterCategoryRequest masterCategoryRequest) throws ResourceAlreadyExistsException;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<?> getAllMasterCategories();

    @GetMapping("/active")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    ResponseEntity<?> getActiveMasterCategories();

    @GetMapping("/{category-id}")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<?> getMasterCategory(@PathVariable(name = "category-id") Integer categoryId) throws ResourceNotFoundException;

    @PutMapping
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<?> updateMasterCategory(@RequestBody MasterCategoryRequest masterCategoryRequest) throws ResourceNotFoundException, ResourceAlreadyExistsException;

    @PutMapping("/{category-id}")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<?> deleteMasterCategory(@PathVariable(name = "category-id") Integer categoryId);

}
