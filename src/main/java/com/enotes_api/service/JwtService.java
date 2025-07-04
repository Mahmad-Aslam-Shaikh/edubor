package com.enotes_api.service;

import com.enotes_api.entity.UserEntity;

public interface JwtService {

    String generateToken(UserEntity userEntity);

}
