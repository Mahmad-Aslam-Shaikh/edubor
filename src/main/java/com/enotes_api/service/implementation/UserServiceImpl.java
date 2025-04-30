package com.enotes_api.service.implementation;

import com.enotes_api.entity.RoleEntity;
import com.enotes_api.entity.UserEntity;
import com.enotes_api.exception.EmailException;
import com.enotes_api.messages.ExceptionMessages;
import com.enotes_api.exception.ResourceAlreadyExistsException;
import com.enotes_api.exception.ResourceNotFoundException;
import com.enotes_api.repository.UserRepository;
import com.enotes_api.request.UserRequest;
import com.enotes_api.response.UserResponse;
import com.enotes_api.service.EmailService;
import com.enotes_api.service.RoleService;
import com.enotes_api.service.UserService;
import com.enotes_api.utility.MapperUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Set;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    private RoleService roleService;

    private EmailService emailService;

    private MapperUtil mapperUtil;

    @Override
    public UserResponse registerUser(UserRequest userRequest) throws ResourceNotFoundException,
            ResourceAlreadyExistsException, EmailException {
        boolean isEmailRegistered = userRepository.existsByEmail(userRequest.getEmail());
        if (isEmailRegistered)
            throw new ResourceAlreadyExistsException(ExceptionMessages.USER_EMAIL_ALREADY_EXISTS_MESSAGE);

        boolean isMobileNoExists = userRepository.existsByMobileNo(userRequest.getMobileNo());
        if (isMobileNoExists)
            throw new ResourceAlreadyExistsException(ExceptionMessages.MOBILE_ALREADY_EXISTS_MESSAGE);

        UserEntity userEntity = mapperUtil.map(userRequest, UserEntity.class);
        if (!ObjectUtils.isEmpty(userRequest.getRoleIds())) {
            Set<RoleEntity> specifiedRoles = roleService.getSpecifiedRoles(userRequest.getRoleIds());
            userEntity.setRoles(specifiedRoles);
        }
        UserEntity savedUser = userRepository.save(userEntity);

        emailService.sendRegistrationMail(savedUser);

        return mapperUtil.map(savedUser, UserResponse.class);
    }

    /*
     * TODO: Write supporting service methods for pending APIs of UserController
     */

}
