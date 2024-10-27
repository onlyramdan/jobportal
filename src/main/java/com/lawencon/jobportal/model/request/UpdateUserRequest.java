package com.lawencon.jobportal.model.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UpdateUserRequest extends CreateUserRequest {
    private String id;
}
