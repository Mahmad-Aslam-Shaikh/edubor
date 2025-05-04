package com.enotes_api.response;

import lombok.Data;

import java.util.List;

@Data
public class UserResponse {

    private Integer id;

    private String firstName;

    private String lastName;

    private String email;

    private String mobileNo;

    private List<RoleResponse> roles;

    private Boolean isActive;

}
