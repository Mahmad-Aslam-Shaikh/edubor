package com.enotes_api.controller;

import com.enotes_api.constants.RouteConstants;
import com.enotes_api.entity.UserEntity;
import com.enotes_api.exception.EmailException;
import com.enotes_api.exception.ResourceAlreadyExistsException;
import com.enotes_api.exception.ResourceNotFoundException;
import com.enotes_api.request.LoginRequest;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping(RouteConstants.USER)
@AllArgsConstructor
public class UserController {

    private UserService userService;

    private MapperUtil mapperUtil;

    @PostMapping
    public ResponseEntity<?> saveUser(@Valid @RequestBody UserRequest userRequest, HttpServletRequest request) throws ResourceNotFoundException,
            ResourceAlreadyExistsException, EmailException {
        UserResponse userResponse = userService.registerUser(userRequest, request);
        return ResponseUtils.createSuccessResponse(userResponse, HttpStatus.CREATED);
    }

    @GetMapping("/{user-id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<?> getUserById(@PathVariable(name = "user-id") Integer userId) throws ResourceNotFoundException {
        UserEntity userEntity = userService.getUserById(userId);
        UserResponse userResponse = mapperUtil.map(userEntity, UserResponse.class);
        return ResponseUtils.createSuccessResponse(userResponse, HttpStatus.OK);
    }

    @PostMapping(RouteConstants.LOGIN)
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        LoginResponse loginResponse = userService.logIn(loginRequest);
        if (!ObjectUtils.isEmpty(loginResponse))
            return ResponseUtils.createSuccessResponse(loginResponse, HttpStatus.OK);
        return ResponseUtils.createFailureResponseWithMessage(HttpStatus.BAD_REQUEST, "Invalid Credentials");
    }

    @PutMapping("/{userId}/roles")
    // This API replaces the previously assigned roles with new role
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<?> updateUserRoles(@PathVariable Integer userId,
                                             @RequestBody(required = false) Set<Integer> roleIds) throws ResourceNotFoundException {
        if (CollectionUtils.isEmpty(roleIds))
            return ResponseUtils.createFailureResponseWithMessage(HttpStatus.BAD_REQUEST, "At least one role must be " +
                    "selected");

        UserEntity updatedUserEntity = userService.updateUserRoles(userId, roleIds);
        UserResponse userResponse = mapperUtil.map(updatedUserEntity, UserResponse.class);
        return ResponseUtils.createSuccessResponse(userResponse, HttpStatus.OK);
    }

    @GetMapping("/profile")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<?> getLoggedInUser() {
        UserEntity loggedInUser = userService.getCurrentLoggedInUser();
        UserResponse loggedInUserResponse = mapperUtil.map(loggedInUser, UserResponse.class);
        return ResponseUtils.createSuccessResponse(loggedInUserResponse, HttpStatus.OK);
    }

    /*
     * TODO: Write APIs for CRUD operation
     *  GET: user by userId
     *  UPDATE: user by userId, make sure only email field cannot be updated, should support assign or de assign
     *  roles also
     *  DELETE: user by userId
     *  GET: all users with specified role or roles
     */


}
