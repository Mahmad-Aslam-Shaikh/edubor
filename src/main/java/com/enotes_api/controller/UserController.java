package com.enotes_api.controller;

import com.enotes_api.exception.ResourceNotFoundException;
import com.enotes_api.request.UserRequest;
import com.enotes_api.response.ResponseUtils;
import com.enotes_api.response.UserResponse;
import com.enotes_api.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
@AllArgsConstructor
public class UserController {

    private UserService userService;

    @PostMapping
    public ResponseEntity<?> saveUser(@Valid @RequestBody UserRequest userRequest) throws ResourceNotFoundException {
        UserResponse userResponse = userService.registerUser(userRequest);
        return ResponseUtils.createSuccessResponse(userResponse, HttpStatus.CREATED);
    }


}
