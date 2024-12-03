package com.lawencon.jobportal.model.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UpdateUserRequest{
    private String id;
    private String username;
    private String email;
    private String roleId;
    private Boolean isActive;
}
