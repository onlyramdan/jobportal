package com.lawencon.jobportal.model.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CreateProfileRequest {
    private String userId;
    private String fullName;
    private String phoneNumber;
    private String address;
}
