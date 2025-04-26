package com.enotes_api.service;

import com.enotes_api.entity.RoleEntity;
import com.enotes_api.exception.ResourceAlreadyExistsException;
import com.enotes_api.exception.ResourceNotFoundException;
import com.enotes_api.request.RoleRequest;
import com.enotes_api.response.RoleResponse;

import java.util.List;
import java.util.Set;

public interface RoleService {

    RoleResponse createRole(RoleRequest roleRequest) throws ResourceAlreadyExistsException;

    RoleEntity getRoleById(Integer roleId) throws ResourceNotFoundException;

    RoleEntity findRoleByName(String roleName);

    Set<RoleEntity> getSpecifiedRoles(Set<Integer> roles) throws ResourceNotFoundException;

    List<RoleResponse> getAllRoles();

    RoleResponse updateRole(Integer roleId, RoleRequest roleRequest) throws ResourceAlreadyExistsException,
            ResourceNotFoundException;

    boolean deleteRole(Integer roleId);
}
