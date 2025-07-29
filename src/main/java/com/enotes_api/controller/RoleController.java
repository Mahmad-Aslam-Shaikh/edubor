package com.enotes_api.controller;

import com.enotes_api.endpoint.RoleEndpoint;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class RoleController implements RoleEndpoint {

    private RoleService roleService;

    private MapperUtil mapperUtil;

    @Override
    public ResponseEntity<?> saveRole(@Valid @RequestBody RoleRequest roleRequest) throws ResourceAlreadyExistsException {
        RoleResponse savedRole = roleService.createRole(roleRequest);
        return ResponseUtils.createSuccessResponse(savedRole, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<?> getAllRoles() {
        List<RoleResponse> allRoles = roleService.getAllRoles();
        return ResponseUtils.createSuccessResponse(allRoles, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getRoleById(@PathVariable(name = "role-id") Integer roleId) throws ResourceNotFoundException {
        RoleEntity roleEntity = roleService.getRoleById(roleId);
        RoleResponse roleResponse = mapperUtil.map(roleEntity, RoleResponse.class);
        return ResponseUtils.createSuccessResponse(roleResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> updateRole(@PathVariable(name = "role-id") Integer roleId,
                                        @RequestBody RoleRequest roleRequest) throws ResourceNotFoundException,
            ResourceAlreadyExistsException {
        RoleResponse updatedRoleResponse = roleService.updateRole(roleId, roleRequest);
        return ResponseUtils.createSuccessResponse(updatedRoleResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> deleteRole(@PathVariable(name = "role-id") Integer roleId) {
        boolean isRoleDeleted = roleService.deleteRole(roleId);
        if (isRoleDeleted) {
            return ResponseUtils.createSuccessResponseWithMessage(HttpStatus.OK, "Role deleted Successfully");
        }
        return ResponseUtils.createSuccessResponseWithMessage(HttpStatus.NOT_FOUND,
                "Role Not Found with id = " + roleId);
    }

}
