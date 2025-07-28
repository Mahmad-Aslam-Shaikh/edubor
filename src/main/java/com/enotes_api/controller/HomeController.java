package com.enotes_api.controller;

import com.enotes_api.constants.RouteConstants;
import com.enotes_api.entity.UserEntity;
import com.enotes_api.exception.EmailException;
import com.enotes_api.exception.InvalidVerificationLinkException;
import com.enotes_api.exception.PasswordChangeException;
import com.enotes_api.exception.ResourceAlreadyVerifiedException;
import com.enotes_api.exception.ResourceNotFoundException;
import com.enotes_api.request.PasswordResetRequest;
import com.enotes_api.response.ResponseUtils;
import com.enotes_api.service.UserService;
import com.enotes_api.utility.MapperUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(RouteConstants.HOME)
@AllArgsConstructor
public class HomeController {

    private UserService userService;

    private MapperUtil mapperUtil;

    @GetMapping(RouteConstants.USER_VERIFY)
    public ResponseEntity<?> verifyUserEmail(@RequestParam(name = "user-id") Integer userId,
                                             @RequestParam(name = "code") String verificationCode) throws
            InvalidVerificationLinkException, ResourceNotFoundException, ResourceAlreadyVerifiedException {
        UserEntity verifiedUser = userService.verifyUser(userId, verificationCode);
        if (verifiedUser.getIsActive())
            return ResponseUtils.createSuccessResponseWithMessage(HttpStatus.OK, "User verified successfully.");

        return ResponseUtils.createFailureResponseWithMessage(HttpStatus.UNPROCESSABLE_ENTITY, "Failed to verify User");
    }

    @PostMapping(RouteConstants.USER_PASSWORD_RESET_EMAIL)
    public ResponseEntity<?> sendPasswordResetMail(@RequestParam String userEmail, HttpServletRequest request) throws ResourceNotFoundException, EmailException {
        userService.sendPasswordResetMail(userEmail, request);
        return ResponseUtils.createSuccessResponseWithMessage(HttpStatus.OK, "Password reset link sent successfully.");
    }

    @GetMapping(RouteConstants.USER_PASSWORD_VERIFY)
    public ResponseEntity<?> verifyPasswordResetLink(@RequestParam(name = "user-id") Integer userId,
                                                     @RequestParam(name = "code") String passwordResetCodeRequest) throws ResourceNotFoundException {
        boolean isPasswordResetLinkValid = userService.verifyUserForPasswordReset(userId, passwordResetCodeRequest);
        if (isPasswordResetLinkValid)
            return ResponseUtils.createSuccessResponseWithMessage(HttpStatus.OK, "Proceed with UI for Password Reset");

        return ResponseUtils.createFailureResponseWithMessage(HttpStatus.FORBIDDEN, "Error occurred while proceeding " +
                "to Password Reset");
    }

    @PutMapping(RouteConstants.USER_PASSWORD_RESET)
    public ResponseEntity<?> resetUserPassword(@RequestParam Integer userId,
                                               @Valid @RequestBody PasswordResetRequest passwordResetRequest) throws ResourceNotFoundException, PasswordChangeException {
        userService.resetUserPassword(userId, passwordResetRequest);
        return ResponseUtils.createSuccessResponseWithMessage(HttpStatus.OK, "Password reset Successful.");
    }

}
