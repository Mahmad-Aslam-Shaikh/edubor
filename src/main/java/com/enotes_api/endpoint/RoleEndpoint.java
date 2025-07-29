package com.enotes_api.endpoint;

import com.enotes_api.exception.ResourceAlreadyExistsException;
import com.enotes_api.exception.ResourceNotFoundException;
import com.enotes_api.request.RoleRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/v1/role")
public interface RoleEndpoint {

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<?> saveRole(@Valid @RequestBody RoleRequest roleRequest) throws ResourceAlreadyExistsException;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<?> getAllRoles();

    @GetMapping("/{role-id}")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<?> getRoleById(@PathVariable(name = "role-id") Integer roleId) throws ResourceNotFoundException;

    @PutMapping("/{role-id}")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<?> updateRole(@PathVariable(name = "role-id") Integer roleId,
                                 @RequestBody RoleRequest roleRequest) throws ResourceNotFoundException,
            ResourceAlreadyExistsException;

    @DeleteMapping("/{role-id}")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<?> deleteRole(@PathVariable(name = "role-id") Integer roleId);

}
