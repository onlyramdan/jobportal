package com.lawencon.jobportal.service;

import com.lawencon.jobportal.model.request.CreateJobDescriptionRequest;
import com.lawencon.jobportal.model.response.JobDescriptionResponse;

import java.util.List;

public interface JobDescriptionService {
    JobDescriptionResponse save(CreateJobDescriptionRequest request);
    JobDescriptionResponse update(String id, CreateJobDescriptionRequest request);
    JobDescriptionResponse getById(String id);
    List<JobDescriptionResponse> getByJobTitleId(String jobTitleId);
    void delete(String id);
}
