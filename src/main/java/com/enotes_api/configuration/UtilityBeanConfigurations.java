package com.enotes_api.configuration;

import com.enotes_api.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;

@Configuration
public class UtilityBeanConfigurations {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public AuditorAware<Integer> auditAware(UserService userService) {
        return new AuditorAwareConfig(userService);
    }

}
