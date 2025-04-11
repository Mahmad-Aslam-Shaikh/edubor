package com.enotes_api.exception;

import lombok.Data;

public class ResourceNotFoundException extends Exception {

    String message;

    public ResourceNotFoundException(String message) {
        super(message);
    }

}
