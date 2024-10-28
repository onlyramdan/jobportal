package com.lawencon.jobportal.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateUserRequest {
    private String  username;
    private String  password;
    private String  email;
    private String  roleId;
}
