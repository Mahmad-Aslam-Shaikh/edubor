package com.enotes_api.service;

import com.enotes_api.entity.UserEntity;
import com.enotes_api.exception.EmailException;

import java.util.Map;

public interface EmailService {

    void sendRegistrationMail(UserEntity user) throws EmailException;

}
