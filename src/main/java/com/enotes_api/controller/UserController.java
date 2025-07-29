package com.enotes_api.controller;

import com.enotes_api.endpoint.UserEndpoint;
import com.enotes_api.entity.UserEntity;
import com.enotes_api.exception.EmailException;
import com.enotes_api.exception.PasswordChangeException;
import com.enotes_api.exception.ResourceAlreadyExistsException;
import com.enotes_api.exception.ResourceNotFoundException;
import com.enotes_api.request.LoginRequest;
import com.enotes_api.request.PasswordChangeRequest;
import com.enotes_api.request.UserRequest;
import com.enotes_api.response.LoginResponse;
import com.enotes_api.response.ResponseUtils;
import com.enotes_api.response.UserResponse;
import com.enotes_api.service.UserService;
import com.enotes_api.utility.MapperUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@AllArgsConstructor
public class UserController implements UserEndpoint {

    private UserService userService;

    private MapperUtil mapperUtil;

    @Override
    public ResponseEntity<?> saveUser(@Valid @RequestBody UserRequest userRequest, HttpServletRequest request) throws ResourceNotFoundException, ResourceAlreadyExistsException, EmailException {
        UserResponse userResponse = userService.registerUser(userRequest, request);
        return ResponseUtils.createSuccessResponse(userResponse, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<?> getUserById(@PathVariable(name = "user-id") Integer userId) throws ResourceNotFoundException {
        UserEntity userEntity = userService.getUserById(userId);
        UserResponse userResponse = mapperUtil.map(userEntity, UserResponse.class);
        return ResponseUtils.createSuccessResponse(userResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        LoginResponse loginResponse = userService.logIn(loginRequest);
        if (!ObjectUtils.isEmpty(loginResponse))
            return ResponseUtils.createSuccessResponse(loginResponse, HttpStatus.OK);
        return ResponseUtils.createFailureResponseWithMessage(HttpStatus.BAD_REQUEST, "Invalid Credentials");
    }

    // This API replaces the previously assigned roles with new role
    @Override
    public ResponseEntity<?> updateUserRoles(@PathVariable Integer userId,
                                             @RequestBody(required = false) Set<Integer> roleIds) throws ResourceNotFoundException {
        if (CollectionUtils.isEmpty(roleIds))
            return ResponseUtils.createFailureResponseWithMessage(HttpStatus.BAD_REQUEST, "At least one role must be "
                    + "selected");

        UserEntity updatedUserEntity = userService.updateUserRoles(userId, roleIds);
        UserResponse userResponse = mapperUtil.map(updatedUserEntity, UserResponse.class);
        return ResponseUtils.createSuccessResponse(userResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getLoggedInUser() {
        UserEntity loggedInUser = userService.getCurrentLoggedInUser();
        UserResponse loggedInUserResponse = mapperUtil.map(loggedInUser, UserResponse.class);
        return ResponseUtils.createSuccessResponse(loggedInUserResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> changePassword(@Valid @RequestBody PasswordChangeRequest passwordChangeRequest) throws PasswordChangeException {
        userService.changeUserPassword(passwordChangeRequest);
        return ResponseUtils.createSuccessResponse("Password updated successfully", HttpStatus.OK);
    }

}
