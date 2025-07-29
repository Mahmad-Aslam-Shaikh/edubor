package com.enotes_api.endpoint;

import com.enotes_api.constants.RouteConstants;
import com.enotes_api.exception.EmailException;
import com.enotes_api.exception.PasswordChangeException;
import com.enotes_api.exception.ResourceAlreadyExistsException;
import com.enotes_api.exception.ResourceNotFoundException;
import com.enotes_api.request.LoginRequest;
import com.enotes_api.request.PasswordChangeRequest;
import com.enotes_api.request.UserRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Set;

@RequestMapping(RouteConstants.USER)
public interface UserEndpoint {

    @PostMapping
    ResponseEntity<?> saveUser(@Valid @RequestBody UserRequest userRequest, HttpServletRequest request) throws ResourceNotFoundException, ResourceAlreadyExistsException, EmailException;

    @GetMapping("/{user-id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    ResponseEntity<?> getUserById(@PathVariable(name = "user-id") Integer userId) throws ResourceNotFoundException;

    @PostMapping(RouteConstants.LOGIN)
    ResponseEntity<?> login(@RequestBody LoginRequest loginRequest);

    @PutMapping("/{userId}/roles")
    // This API replaces the previously assigned roles with new role
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    ResponseEntity<?> updateUserRoles(@PathVariable Integer userId,
                                      @RequestBody(required = false) Set<Integer> roleIds) throws ResourceNotFoundException;

    @GetMapping("/profile")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    ResponseEntity<?> getLoggedInUser();

    @PutMapping("/password")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    ResponseEntity<?> changePassword(@Valid @RequestBody PasswordChangeRequest passwordChangeRequest) throws PasswordChangeException;

}
