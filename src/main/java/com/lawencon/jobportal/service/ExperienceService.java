package com.lawencon.jobportal.service;

import com.lawencon.jobportal.model.request.CreateExperienceRequest;
import com.lawencon.jobportal.model.response.ExperienceResponse;
import com.lawencon.jobportal.persistent.entity.Experience;

import java.util.List;

public interface ExperienceService {
    ExperienceResponse save(CreateExperienceRequest request);
    ExperienceResponse update(CreateExperienceRequest request, String id);
    void delete(String id);
    ExperienceResponse getById(String id);
    List<ExperienceResponse> getAllByProfileId(String profileId);
    Experience getEntityById(String id);

}
