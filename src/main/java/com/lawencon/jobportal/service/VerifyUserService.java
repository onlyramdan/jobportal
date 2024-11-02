package com.lawencon.jobportal.service;

import com.lawencon.jobportal.model.request.CreateUserRequest;
import com.lawencon.jobportal.model.request.VerifyUserRequest;

public interface VerifyUserService {
     void register(CreateUserRequest request);
     void resend(String email);
     void verify(VerifyUserRequest request);
}
