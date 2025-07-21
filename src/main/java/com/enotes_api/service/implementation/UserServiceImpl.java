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
import com.enotes_api.request.LoginRequest;
import com.enotes_api.request.UserRequest;
import com.enotes_api.response.LoginResponse;
import com.enotes_api.response.UserResponse;
import com.enotes_api.security.CustomUserDetails;
import com.enotes_api.service.EmailService;
import com.enotes_api.service.JwtService;
import com.enotes_api.service.RoleService;
import com.enotes_api.service.UserService;
import com.enotes_api.utility.MapperUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Set;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private AuthenticationManager authenticationManager;

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private UserRepository userRepository;

    private RoleService roleService;

    private EmailService emailService;

    private MapperUtil mapperUtil;

    private JwtService jwtService;

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
        userEntity.setPassword(bCryptPasswordEncoder.encode(userRequest.getPassword()));

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
            throw new ResourceAlreadyVerifiedException(ExceptionMessages.USER_ALREADY_VERIFIED_MESSAGE);

        if (userVerificationDetails.getVerificationCode().equals(verificationCode))
            unVerifiedUser.setIsActive(Boolean.TRUE);
        else
            throw new InvalidVerificationLinkException(ExceptionMessages.USER_VERIFICATION_LINK_INVALID_MESSAGE);

        unVerifiedUser.setUserVerificationStatus(null);

        return userRepository.save(unVerifiedUser);
    }

    @Override
    public UserEntity getUserByEmail(String email) throws ResourceNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException(ExceptionMessages.USER_NOT_FOUND_WITH_EMAIL_MESSAGE + email));
    }

    @Override
    public LoginResponse logIn(LoginRequest loginRequest) {
        UsernamePasswordAuthenticationToken userNamePasswordRequest =
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword());
        Authentication authentication = authenticationManager.authenticate(userNamePasswordRequest);

        if (authentication.isAuthenticated()) {
            CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
            UserEntity userEntity = customUserDetails.getUserEntity();
            String jwtToken = jwtService.generateToken(userEntity);

            return LoginResponse.builder()
                    .userResponse(mapperUtil.map(userEntity, UserResponse.class))
                    .token(jwtToken)
                    .build();
        }

        return null;
    }

    @Override
    public UserEntity updateUserRoles(Integer userId, Set<Integer> roleIds) throws ResourceNotFoundException {
        UserEntity userEntity = getUserById(userId);
        Set<RoleEntity> rolesToBeAssigned = roleService.getSpecifiedRoles(roleIds);

        userEntity.setRoles(rolesToBeAssigned);
        return userRepository.save(userEntity);
    }

    /*
     * TODO: Write supporting service methods for pending APIs of UserController
     */

}
