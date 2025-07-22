package com.enotes_api.configuration;

import com.enotes_api.entity.UserEntity;
import com.enotes_api.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

@AllArgsConstructor
public class AuditorAwareConfig implements AuditorAware<Integer> {

    private UserService userService;

    @Override
    public Optional<Integer> getCurrentAuditor() {
        UserEntity currentLoggedInUser = userService.getCurrentLoggedInUser();
        return Optional.of(currentLoggedInUser.getId());
    }

}
