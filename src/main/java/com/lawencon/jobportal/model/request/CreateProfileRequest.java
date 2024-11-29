package com.lawencon.jobportal.model.request;

import com.lawencon.jobportal.persistent.entity.User;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CreateProfileRequest {
    private User user;
}
