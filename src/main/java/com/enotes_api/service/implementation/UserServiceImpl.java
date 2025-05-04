package com.enotes_api.service.implementation;

import com.enotes_api.entity.RoleEntity;
import com.enotes_api.entity.UserAccountVerificationEntity;
import com.enotes_api.entity.UserEntity;
import com.enotes_api.exception.EmailException;
import com.enotes_api.exception.InvalidVerificationLinkException;
import com.enotes_api.exception.ResourceAlreadyExistsException;
import com.enotes_api.exception.ResourceAlreadyVerifiedException;
import com.enotes_api.exception.ResourceNotFoundException;
import com.enotes_api.messages.ExceptionMessages;
import com.enotes_api.repository.UserRepository;
import com.enotes_api.request.UserRequest;
import com.enotes_api.response.UserResponse;
import com.enotes_api.service.EmailService;
import com.enotes_api.service.RoleService;
import com.enotes_api.service.UserService;
import com.enotes_api.utility.MapperUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Set;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    private RoleService roleService;

    private EmailService emailService;

    private MapperUtil mapperUtil;

    @Override
    public UserResponse registerUser(UserRequest userRequest, HttpServletRequest request) throws ResourceNotFoundException,
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

        UserAccountVerificationEntity userVerificationDetails = UserAccountVerificationEntity.builder()
                .verificationCode(UUID.randomUUID().toString())
                .build();
        userEntity.setUserVerificationStatus(userVerificationDetails);
        userEntity.setIsActive(Boolean.FALSE);

        UserEntity savedUser = userRepository.save(userEntity);

        emailService.sendRegistrationMail(savedUser, request);

        return mapperUtil.map(savedUser, UserResponse.class);
    }

    @Override
    public UserEntity getUserById(Integer userId) throws ResourceNotFoundException {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(ExceptionMessages.USER_NOT_FOUND_MESSAGE + userId));
    }

    @Override
    public UserEntity verifyUser(Integer userId, String verificationCode) throws ResourceNotFoundException,
            InvalidVerificationLinkException, ResourceAlreadyVerifiedException {
        UserEntity unVerifiedUser = getUserById(userId);
        UserAccountVerificationEntity userVerificationDetails = unVerifiedUser.getUserVerificationStatus();

        if (unVerifiedUser.getIsActive() || ObjectUtils.isEmpty(userVerificationDetails))
            throw new ResourceAlreadyVerifiedException(ExceptionMessages.USER_ALREADY_VERIFIED);

        if (userVerificationDetails.getVerificationCode().equals(verificationCode))
            unVerifiedUser.setIsActive(Boolean.TRUE);
        else
            throw new InvalidVerificationLinkException(ExceptionMessages.USER_VERIFICATION_LINK_INVALID);

        unVerifiedUser.setUserVerificationStatus(null);

        return userRepository.save(unVerifiedUser);
    }

    /*
     * TODO: Write supporting service methods for pending APIs of UserController
     */

}
