package com.enotes_api.exception;

import com.enotes_api.response.ResponseUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException exception) {
        log.error("GlobalExceptionHandler : handleResourceNotFoundException() : {}", exception.getMessage());
        return ResponseUtils.createFailureResponseWithMessage(HttpStatus.NOT_FOUND, exception.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        log.error("GlobalExceptionHandler : handleMethodArgumentNotValidException() : {}", exception.getMessage());
        Map<String, String> errors = new HashMap<>();
        exception.getBindingResult().getFieldErrors()
                .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
        return ResponseUtils.createFailureResponse(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ResponseEntity<?> handleResourceAlreadyExistsException(ResourceAlreadyExistsException exception) {
        log.error("GlobalExceptionHandler : handleResourceAlreadyExistsException() : {}", exception.getMessage());
        return ResponseUtils.createFailureResponseWithMessage(HttpStatus.CONFLICT, exception.getMessage());
    }

    @ExceptionHandler(InvalidFileException.class)
    public ResponseEntity<?> handleInvalidFileException(InvalidFileException exception) {
        log.error("GlobalExceptionHandler : handleInvalidFileException() : {}", exception.getMessage());
        return ResponseUtils.createFailureResponseWithMessage(HttpStatus.BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(EmailException.class)
    public ResponseEntity<?> handleEmailException(EmailException exception) {
        log.error("GlobalExceptionHandler : handleEmailException() : {}", exception.getMessage());
        return ResponseUtils.createFailureResponseWithMessage(HttpStatus.SERVICE_UNAVAILABLE, exception.getMessage());
    }

    @ExceptionHandler(InvalidVerificationLinkException.class)
    public ResponseEntity<?> handleInvalidVerificationLinkException(InvalidVerificationLinkException exception) {
        log.error("GlobalExceptionHandler : handleInvalidVerificationLinkException() : {}", exception.getMessage());
        return ResponseUtils.createFailureResponseWithMessage(HttpStatus.BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(ResourceAlreadyVerifiedException.class)
    public ResponseEntity<?> handleResourceAlreadyVerifiedException(ResourceAlreadyVerifiedException exception) {
        log.error("GlobalExceptionHandler : handleResourceAlreadyVerifiedException() : {}", exception.getMessage());
        return ResponseUtils.createSuccessResponseWithMessage(HttpStatus.BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<?> handleBadCredentialsException(BadCredentialsException exception) {
        log.error("GlobalExceptionHandler : handleBadCredentialsException() : {}", exception.getMessage());
        return ResponseUtils.createFailureResponseWithMessage(HttpStatus.BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<?> handleAuthorizationDeniedException(AuthorizationDeniedException exception) {
        log.error("GlobalExceptionHandler : handleAuthorizationDeniedException() : {}", exception.getMessage());
        return ResponseUtils.createFailureResponseWithMessage(HttpStatus.FORBIDDEN, exception.getMessage());
    }

    @ExceptionHandler(PasswordChangeException.class)
    public ResponseEntity<?> handlePasswordChangeException(PasswordChangeException exception) {
        log.error("GlobalExceptionHandler : handlePasswordChangeException() : {}", exception.getMessage());
        return ResponseUtils.createFailureResponseWithMessage(HttpStatus.BAD_REQUEST, exception.getMessage());
    }


}
