package com.enotes_api.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PasswordChangeRequest {

    @NotBlank(message = "Old password must not be empty")
    private String oldPassword;

    @NotBlank(message = "New password required")
    private String newPassword;

}
