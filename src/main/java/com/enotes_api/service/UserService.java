package com.enotes_api.service;

import com.enotes_api.entity.UserEntity;
import com.enotes_api.exception.EmailException;
import com.enotes_api.exception.InvalidVerificationLinkException;
import com.enotes_api.exception.ResourceAlreadyExistsException;
import com.enotes_api.exception.ResourceAlreadyVerifiedException;
import com.enotes_api.exception.ResourceNotFoundException;
import com.enotes_api.request.LoginRequest;
import com.enotes_api.request.UserRequest;
import com.enotes_api.response.LoginResponse;
import com.enotes_api.response.UserResponse;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Set;

public interface UserService {

    UserResponse registerUser(UserRequest userRequest, HttpServletRequest request) throws ResourceNotFoundException,
            ResourceAlreadyExistsException, EmailException;

    UserEntity getUserById(Integer userId) throws ResourceNotFoundException;

    UserEntity verifyUser(Integer userId, String verificationCode) throws ResourceNotFoundException,
            InvalidVerificationLinkException, ResourceAlreadyVerifiedException;

    UserEntity getUserByEmail(String email) throws ResourceNotFoundException;

    LoginResponse logIn(LoginRequest loginRequest);

    UserEntity updateUserRoles(Integer userId, Set<Integer> roleIds) throws ResourceNotFoundException;

    UserEntity getCurrentLoggedInUser();
}
