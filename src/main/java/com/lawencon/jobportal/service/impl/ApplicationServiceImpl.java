package com.lawencon.jobportal.service.impl;

import com.lawencon.jobportal.model.request.ApplicationRequest;
import com.lawencon.jobportal.model.response.ApplicationResponse;
import com.lawencon.jobportal.persistent.entity.Application;
import com.lawencon.jobportal.persistent.repository.AplicationRepository;
import com.lawencon.jobportal.service.ApplicationService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ApplicationServiceImpl implements ApplicationService {

    private final AplicationRepository applicationRepository;

    @Override
    @Transactional
    public ApplicationResponse createApplication(ApplicationRequest request) {
        return null;
    }

    @Override
    public List<ApplicationResponse> getAllApplications() {
        List<Application> applications = applicationRepository.findAll();
        return applications.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ApplicationResponse getApplicationById(String id) {
        return null;
    }

    private ApplicationResponse convertToResponse(Application application) {
        return null;
    }
}
