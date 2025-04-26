package com.enotes_api.service.implementation;

import com.enotes_api.entity.RoleEntity;
import com.enotes_api.entity.UserEntity;
import com.enotes_api.exception.ResourceNotFoundException;
import com.enotes_api.repository.UserRepository;
import com.enotes_api.request.UserRequest;
import com.enotes_api.response.UserResponse;
import com.enotes_api.service.RoleService;
import com.enotes_api.service.UserService;
import com.enotes_api.utility.MapperUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    private RoleService roleService;

    private MapperUtil mapperUtil;

    @Override
    public UserResponse registerUser(UserRequest userRequest) throws ResourceNotFoundException {
        UserEntity userEntity = mapperUtil.map(userRequest, UserEntity.class);
        if (!ObjectUtils.isEmpty(userRequest.getRoleIds())) {
            List<RoleEntity> specifiedRoles = roleService.getSpecifiedRoles(userRequest.getRoleIds());
            userEntity.setRoles(specifiedRoles);
        }
        UserEntity savedUser = userRepository.save(userEntity);
        return mapperUtil.map(savedUser, UserResponse.class);
    }
}
