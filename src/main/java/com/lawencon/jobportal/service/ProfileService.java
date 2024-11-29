package com.lawencon.jobportal.service;

import com.lawencon.jobportal.model.request.CreateProfileRequest;
import com.lawencon.jobportal.model.response.ProfileResponse;

import com.lawencon.jobportal.persistent.entity.Profile;


public interface ProfileService {
    ProfileResponse save(CreateProfileRequest request);
    ProfileResponse findByUserId(String id);
    ProfileResponse findById(String id);
    Profile findEntityById(String  id);
    ProfileResponse update(CreateProfileRequest request, String id);
    void delete(String id);
    Boolean checkProfileExist(String id);
}
