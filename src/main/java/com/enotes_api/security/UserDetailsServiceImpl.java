package com.enotes_api.security;

import com.enotes_api.entity.UserEntity;
import com.enotes_api.exception.ResourceNotFoundException;
import com.enotes_api.messages.ExceptionMessages;
import com.enotes_api.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        try {
            UserEntity userEntity = userService.getUserByEmail(email);
            CustomUserDetails userDetails = new CustomUserDetails(userEntity);
            System.out.println("Authorities: " + userDetails.getAuthorities());
            return new CustomUserDetails(userEntity);
        } catch (ResourceNotFoundException e) {
            System.out.println(e);
            throw new UsernameNotFoundException(ExceptionMessages.USER_NOT_FOUND_WITH_EMAIL_MESSAGE + email);
        }
    }
}
