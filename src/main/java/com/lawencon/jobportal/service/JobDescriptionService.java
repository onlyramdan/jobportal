package com.lawencon.jobportal.service;

import com.lawencon.jobportal.model.request.CreateJobDescriptionRequest;
import com.lawencon.jobportal.model.request.UpdateDecscriptionRequest;
import com.lawencon.jobportal.model.response.JobDescriptionResponse;

import java.util.List;

public interface JobDescriptionService {
    JobDescriptionResponse save(CreateJobDescriptionRequest request);
    void update(UpdateDecscriptionRequest request);
    JobDescriptionResponse getById(String id);
    List<JobDescriptionResponse> getByJobTitleId(String jobTitleId);
    void delete(String id);
    void saveAll(List<CreateJobDescriptionRequest> requests);
}
