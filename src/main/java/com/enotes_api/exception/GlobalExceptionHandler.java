package com.enotes_api.exception;

import com.enotes_api.response.ResponseUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException exception) {
        return ResponseUtils.createFailureResponseWithMessage(HttpStatus.NOT_FOUND, exception.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        Map<String, String> errors = new HashMap<>();
        exception.getBindingResult().getFieldErrors()
                .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
        return ResponseUtils.createFailureResponse(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ResponseEntity<?> handleResourceAlreadyExistsException(ResourceAlreadyExistsException exception) {
        return ResponseUtils.createFailureResponseWithMessage(HttpStatus.CONFLICT, exception.getMessage());
    }

    @ExceptionHandler(InvalidFileException.class)
    public ResponseEntity<?> handleInvalidFileException(InvalidFileException exception) {
        return ResponseUtils.createFailureResponseWithMessage(HttpStatus.BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(EmailException.class)
    public ResponseEntity<?> handleEmailException(EmailException exception) {
        return ResponseUtils.createFailureResponseWithMessage(HttpStatus.SERVICE_UNAVAILABLE, exception.getMessage());
    }

    @ExceptionHandler(InvalidVerificationLinkException.class)
    public ResponseEntity<?> handleInvalidVerificationLinkException(InvalidVerificationLinkException exception) {
        return ResponseUtils.createFailureResponseWithMessage(HttpStatus.BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(ResourceAlreadyVerifiedException.class)
    public ResponseEntity<?> handleResourceAlreadyVerifiedException(ResourceAlreadyVerifiedException exception) {
        return ResponseUtils.createSuccessResponseWithMessage(HttpStatus.BAD_REQUEST, exception.getMessage());
    }


}
