package com.enotes_api.controller;

import com.enotes_api.constants.RouteConstants;
import com.enotes_api.entity.UserEntity;
import com.enotes_api.exception.InvalidVerificationLinkException;
import com.enotes_api.exception.ResourceAlreadyVerifiedException;
import com.enotes_api.exception.ResourceNotFoundException;
import com.enotes_api.response.ResponseUtils;
import com.enotes_api.service.UserService;
import com.enotes_api.utility.MapperUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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

}
