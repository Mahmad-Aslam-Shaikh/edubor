package com.enotes_api.service;

import com.enotes_api.exception.EmailException;
import com.enotes_api.exception.ResourceAlreadyExistsException;
import com.enotes_api.exception.ResourceNotFoundException;
import com.enotes_api.request.UserRequest;
import com.enotes_api.response.UserResponse;

public interface UserService {

    UserResponse registerUser(UserRequest userRequest) throws ResourceNotFoundException, ResourceAlreadyExistsException, EmailException;

}
