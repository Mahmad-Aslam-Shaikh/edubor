package com.enotes_api.exception;

public class ResourceNotFoundException extends Exception {

    String message;

    public ResourceNotFoundException(String message) {
        super(message);
    }

}
