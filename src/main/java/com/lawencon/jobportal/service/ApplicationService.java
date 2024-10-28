package com.lawencon.jobportal.service;

import com.lawencon.jobportal.model.request.ApplicationRequest;
import com.lawencon.jobportal.model.response.ApplicationResponse;

import java.util.List;

public interface ApplicationService {
    ApplicationResponse createApplication(ApplicationRequest request);
    List<ApplicationResponse> getAllApplications();
    ApplicationResponse getApplicationById(String id);
}
