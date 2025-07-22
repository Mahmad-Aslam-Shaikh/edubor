package com.enotes_api.controller;

import com.enotes_api.entity.RoleEntity;
import com.enotes_api.exception.ResourceAlreadyExistsException;
import com.enotes_api.exception.ResourceNotFoundException;
import com.enotes_api.request.RoleRequest;
import com.enotes_api.response.ResponseUtils;
import com.enotes_api.response.RoleResponse;
import com.enotes_api.service.RoleService;
import com.enotes_api.utility.MapperUtil;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/role")
@AllArgsConstructor
public class RoleController {

    private RoleService roleService;

    private MapperUtil mapperUtil;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> saveRole(@Valid @RequestBody RoleRequest roleRequest) throws ResourceAlreadyExistsException {
        RoleResponse savedRole = roleService.createRole(roleRequest);
        return ResponseUtils.createSuccessResponse(savedRole, HttpStatus.CREATED);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAllRoles() {
        List<RoleResponse> allRoles = roleService.getAllRoles();
        return ResponseUtils.createSuccessResponse(allRoles, HttpStatus.OK);
    }

    @GetMapping("/{role-id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getRoleById(@PathVariable(name = "role-id") Integer roleId) throws ResourceNotFoundException {
        RoleEntity roleEntity = roleService.getRoleById(roleId);
        RoleResponse roleResponse = mapperUtil.map(roleEntity, RoleResponse.class);
        return ResponseUtils.createSuccessResponse(roleResponse, HttpStatus.OK);
    }

    @PutMapping("/{role-id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateRole(@PathVariable(name = "role-id") Integer roleId,
                                        @RequestBody RoleRequest roleRequest) throws ResourceNotFoundException,
            ResourceAlreadyExistsException {
        RoleResponse updatedRoleResponse = roleService.updateRole(roleId, roleRequest);
        return ResponseUtils.createSuccessResponse(updatedRoleResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{role-id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteRole(@PathVariable(name = "role-id") Integer roleId) {
        boolean isRoleDeleted = roleService.deleteRole(roleId);
        if (isRoleDeleted) {
            return ResponseUtils.createSuccessResponseWithMessage(HttpStatus.OK, "Role deleted Successfully");
        }
        return ResponseUtils.createSuccessResponseWithMessage(HttpStatus.NOT_FOUND,
                "Role Not Found with id = " + roleId);
    }

}
