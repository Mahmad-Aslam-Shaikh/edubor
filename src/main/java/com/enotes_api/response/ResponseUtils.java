package com.enotes_api.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseUtils {

    public static ResponseEntity<?> createSuccessResponse(Object data, HttpStatus httpStatus) {
        CommonResponseFormat response = CommonResponseFormat.builder()
                .status("Success")
                .data(data)
                .message("Success")
                .httpStatus(httpStatus)
                .build();
        return response.create();
    }

    public static ResponseEntity<?> createSuccessResponseWithMessage(HttpStatus httpStatus, String message) {
        CommonResponseFormat response = CommonResponseFormat.builder()
                .status("Success")
                .message(message)
                .httpStatus(httpStatus)
                .build();
        return response.create();
    }

    public static ResponseEntity<?> createFailureResponse(Object data, HttpStatus httpStatus) {
        CommonResponseFormat response = CommonResponseFormat.builder()
                .status("Failed")
                .data(data)
                .message("Failed")
                .httpStatus(httpStatus)
                .build();
        return response.create();
    }

    public static ResponseEntity<?> createFailureResponseWithMessage(HttpStatus httpStatus, String message) {
        CommonResponseFormat response = CommonResponseFormat.builder()
                .status("Failed")
                .message(message)
                .httpStatus(httpStatus)
                .build();
        return response.create();
    }


}
