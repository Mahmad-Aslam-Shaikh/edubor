package com.enotes_api.endpoint;

import com.enotes_api.constants.RouteConstants;
import com.enotes_api.exception.EmailException;
import com.enotes_api.exception.InvalidVerificationLinkException;
import com.enotes_api.exception.PasswordChangeException;
import com.enotes_api.exception.ResourceAlreadyVerifiedException;
import com.enotes_api.exception.ResourceNotFoundException;
import com.enotes_api.request.PasswordResetRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping(RouteConstants.HOME)
public interface HomeEndpoint {

    @GetMapping(RouteConstants.USER_VERIFY)
    ResponseEntity<?> verifyUserEmail(@RequestParam(name = "user-id") Integer userId,
                                      @RequestParam(name = "code") String verificationCode) throws
            InvalidVerificationLinkException, ResourceNotFoundException, ResourceAlreadyVerifiedException;

    @PostMapping(RouteConstants.USER_PASSWORD_RESET_EMAIL)
    ResponseEntity<?> sendPasswordResetMail(@RequestParam String userEmail, HttpServletRequest request) throws ResourceNotFoundException, EmailException;

    @GetMapping(RouteConstants.USER_PASSWORD_VERIFY)
    ResponseEntity<?> verifyPasswordResetLink(@RequestParam(name = "user-id") Integer userId,
                                              @RequestParam(name = "code") String passwordResetCodeRequest) throws ResourceNotFoundException;

    @PutMapping(RouteConstants.USER_PASSWORD_RESET)
    ResponseEntity<?> resetUserPassword(@RequestParam Integer userId,
                                        @Valid @RequestBody PasswordResetRequest passwordResetRequest) throws ResourceNotFoundException, PasswordChangeException;

}
