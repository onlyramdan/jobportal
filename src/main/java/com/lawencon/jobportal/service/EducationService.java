package com.lawencon.jobportal.service;

import com.lawencon.jobportal.model.request.CreateEducationRequest;
import com.lawencon.jobportal.model.response.EducationResponse;
import com.lawencon.jobportal.persistent.entity.Education;

import java.util.List;

public interface EducationService {
    List<EducationResponse> getAllByProfileId(String id);
    EducationResponse save(CreateEducationRequest education);
    EducationResponse getById(String id);
    void delete(String id);
    EducationResponse  update(CreateEducationRequest education, String id);
    Education getEntityById(String id);
}
