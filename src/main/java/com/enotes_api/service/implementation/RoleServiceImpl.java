package com.enotes_api.service.implementation;

import com.enotes_api.entity.RoleEntity;
import com.enotes_api.messages.ExceptionMessages;
import com.enotes_api.exception.ResourceAlreadyExistsException;
import com.enotes_api.exception.ResourceNotFoundException;
import com.enotes_api.repository.RoleRepository;
import com.enotes_api.request.RoleRequest;
import com.enotes_api.response.RoleResponse;
import com.enotes_api.service.RoleService;
import com.enotes_api.utility.MapperUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class RoleServiceImpl implements RoleService {

    private RoleRepository roleRepository;

    private MapperUtil mapperUtil;

    @Override
    public RoleResponse createRole(RoleRequest roleRequest) throws ResourceAlreadyExistsException {
        if (!ObjectUtils.isEmpty(findRoleByName(roleRequest.getName())))
            throw new ResourceAlreadyExistsException(ExceptionMessages.ROLE_ALREADY_EXISTS_MESSAGE);

        RoleEntity roleEntity = mapperUtil.map(roleRequest, RoleEntity.class);
        RoleEntity savedRole = roleRepository.save(roleEntity);
        return mapperUtil.map(savedRole, RoleResponse.class);
    }

    @Override
    public RoleEntity getRoleById(Integer roleId) throws ResourceNotFoundException {
        return roleRepository.findById(roleId)
                .orElseThrow(() -> new ResourceNotFoundException(ExceptionMessages.ROLE_NOT_FOUND_MESSAGE + roleId));
    }

    @Override
    public RoleEntity findRoleByName(String roleName) {
        return roleRepository.findByNameIgnoreCase(roleName);
    }

    @Override
    public Set<RoleEntity> getSpecifiedRoles(Set<Integer> roleIds) throws ResourceNotFoundException {
        Set<RoleEntity> requiredRoles = new HashSet<>(roleRepository.findAllById(roleIds));

        if (requiredRoles.size() != roleIds.size())
            throw new ResourceNotFoundException(ExceptionMessages.SOME_ROLE_NOT_FOUND_MESSAGE);

        return requiredRoles;
    }

    @Override
    public List<RoleResponse> getAllRoles() {
        List<RoleEntity> allRoles = roleRepository.findAll();
        return mapperUtil.mapList(allRoles, RoleResponse.class);
    }

    @Override
    public RoleResponse updateRole(Integer roleId, RoleRequest roleRequest) throws ResourceAlreadyExistsException,
            ResourceNotFoundException {
        RoleEntity existingRole = getRoleById(roleId);
        if (roleRequest != null) {
            if (!ObjectUtils.isEmpty(roleRequest.getName())) {
                if (!ObjectUtils.isEmpty(findRoleByName(roleRequest.getName())))
                    throw new ResourceAlreadyExistsException(ExceptionMessages.ROLE_ALREADY_EXISTS_MESSAGE);
                existingRole.setName(roleRequest.getName().toUpperCase());
            }
        }
        RoleEntity updatedRole = roleRepository.save(existingRole);
        return mapperUtil.map(updatedRole, RoleResponse.class);
    }

    @Override
    public boolean deleteRole(Integer roleId) {
        RoleEntity existingRole = null;
        try {
            existingRole = getRoleById(roleId);
        } catch (ResourceNotFoundException e) {
            return Boolean.FALSE;
        }
        roleRepository.delete(existingRole);
        return Boolean.TRUE;
    }

}
