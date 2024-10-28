package com.lawencon.jobportal.service;

import java.util.List;
import com.lawencon.jobportal.model.request.CreateJobSpecificationsRequest;
import com.lawencon.jobportal.model.response.JobSpecificationResponse;

public interface JobSpecificationService {
    List<JobSpecificationResponse> save(CreateJobSpecificationsRequest request);
    JobSpecificationResponse getById(String id);
    List<JobSpecificationResponse> getAllByJobTitleId(String jobTitleId);
    List<JobSpecificationResponse> updateAll(String jobTitleId, CreateJobSpecificationsRequest request);
    void delete(String id);
    JobSpecificationResponse update(String id, CreateJobSpecificationsRequest request);
}
