package com.enotes_api.service;

import com.enotes_api.entity.UserEntity;
import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {

    String generateToken(UserEntity userEntity);

    String extractUserEmailFromToken(String token);

    Boolean validateToken(String token, UserDetails userDetails);

}
