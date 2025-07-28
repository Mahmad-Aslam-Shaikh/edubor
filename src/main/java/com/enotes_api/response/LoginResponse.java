package com.enotes_api.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponse {

    private UserResponse userResponse;

    private String token;

}
