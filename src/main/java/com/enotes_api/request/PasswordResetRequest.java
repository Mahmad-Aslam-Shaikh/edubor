package com.enotes_api.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PasswordResetRequest {

    @NotBlank(message = "New password must not be empty")
    private String newPassword;

    @NotBlank(message = "New password required")
    private String reEnterNewPassword;

}
