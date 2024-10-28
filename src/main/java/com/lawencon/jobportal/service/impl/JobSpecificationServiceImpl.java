package com.lawencon.jobportal.service.impl;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import com.lawencon.jobportal.model.request.CreateJobSpecificationsRequest;
import com.lawencon.jobportal.model.response.JobSpecificationResponse;
import com.lawencon.jobportal.persistent.entity.JobSpecification;
import com.lawencon.jobportal.persistent.entity.JobTitle;
import com.lawencon.jobportal.persistent.repository.JobSpecificationRepository;
import com.lawencon.jobportal.service.JobSpecificationService;
import com.lawencon.jobportal.service.JobTitleService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;


import java.time.ZonedDateTime;
import java.time.ZoneOffset;

@AllArgsConstructor
@Transactional
@Service
public class JobSpecificationServiceImpl implements JobSpecificationService {

    private final JobSpecificationRepository jobSpecificationRepository;
    private final JobTitleService jobTitleService;

    private JobSpecificationResponse convertToResponse(JobSpecification jobSpecification) {
        JobSpecificationResponse response = new JobSpecificationResponse();
        BeanUtils.copyProperties(jobSpecification, response);
        response.setJobTitleId(jobSpecification.getJobTitle().getId());
        return response;
    }

    @Override
    public List<JobSpecificationResponse> save(CreateJobSpecificationsRequest request) {
        JobTitle jobTitle = jobTitleService.findEntityById(request.getJobTitleId());

        List<JobSpecification> jobSpecifications = request.getSpecifications().stream()
            .map(spec -> {
                JobSpecification jobSpecification = new JobSpecification();
                jobSpecification.setJobTitle(jobTitle);
                jobSpecification.setSpecification(spec);
                jobSpecification.setCreatedAt(ZonedDateTime.now(ZoneOffset.UTC));
                jobSpecification.setUpdatedAt(ZonedDateTime.now(ZoneOffset.UTC));
                jobSpecification.setCreatedBy("system");
                jobSpecification.setUpdatedBy("system");
                jobSpecification.setIsActive(true);
                return jobSpecification;
            })
            .collect(Collectors.toList());

        List<JobSpecification> savedSpecifications = jobSpecificationRepository.saveAll(jobSpecifications);
        return savedSpecifications.stream().map(this::convertToResponse).collect(Collectors.toList());
    }

    @Override
    public JobSpecificationResponse getById(String id) {
        return convertToResponse(getEntityById(id));
    }

    @Override
    public List<JobSpecificationResponse> getAllByJobTitleId(String jobTitleId) {
        return jobSpecificationRepository.findByJobTitleId(jobTitleId).stream()
            .map(this::convertToResponse)
            .collect(Collectors.toList());
    }
    
    @Override
    public List<JobSpecificationResponse> updateAll(String jobTitleId, CreateJobSpecificationsRequest request) {
        jobSpecificationRepository.deleteByJobTitleId(jobTitleId);
        return save(request);
    }
    
    public JobSpecificationResponse update(String id, CreateJobSpecificationsRequest request) {
        JobSpecification jobSpecification = getEntityById(id);
        BeanUtils.copyProperties(request, jobSpecification);
        return  convertToResponse(jobSpecificationRepository.save(jobSpecification));

    }
    


    @Override
    public void delete(String id) {
        JobSpecification jobSpecification = getEntityById(id);
        jobSpecification.setDeletedAt(ZonedDateTime.now());
        jobSpecification.setIsActive(false);
        jobSpecificationRepository.save(jobSpecification);
    }

    private JobSpecification getEntityById(String id) {
        return jobSpecificationRepository.findById(id).orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Job Specification not found"));
    }
}
