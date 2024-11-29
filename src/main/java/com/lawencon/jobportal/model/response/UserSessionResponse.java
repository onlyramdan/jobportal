package com.lawencon.jobportal.model.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserSessionResponse {
    private String id;
    private String roleCode;
}
    