package com.enotes_api.service;

import com.enotes_api.entity.UserEntity;
import com.enotes_api.exception.EmailException;
import jakarta.servlet.http.HttpServletRequest;

public interface EmailService {

    void sendRegistrationMail(UserEntity user, HttpServletRequest request) throws EmailException;

    void sendPasswordResetMail(UserEntity user, HttpServletRequest request) throws EmailException;

}
