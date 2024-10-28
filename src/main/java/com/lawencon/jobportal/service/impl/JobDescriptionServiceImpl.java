package com.lawencon.jobportal.service.impl;

import com.lawencon.jobportal.model.request.CreateJobDescriptionRequest;
import com.lawencon.jobportal.model.response.JobDescriptionResponse;
import com.lawencon.jobportal.persistent.entity.JobDescription;
import com.lawencon.jobportal.persistent.entity.JobTitle;
import com.lawencon.jobportal.persistent.repository.JobDescriptionRepository;
import com.lawencon.jobportal.service.JobDescriptionService;
import com.lawencon.jobportal.service.JobTitleService;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

import java.time.ZonedDateTime;
import java.time.ZoneOffset;

@AllArgsConstructor
@Transactional
@Service
public class JobDescriptionServiceImpl implements JobDescriptionService {

    private final JobDescriptionRepository jobDescriptionRepository;
    private final JobTitleService jobTitleService;

    private JobDescriptionResponse convertToResponse(JobDescription jobDescription) {
        JobDescriptionResponse response = new JobDescriptionResponse();
        BeanUtils.copyProperties(jobDescription, response);
        response.setJobTitleId(jobDescription.getJobTitle().getId());
        return response;
    }

    @Override
    public JobDescriptionResponse save(CreateJobDescriptionRequest request) {
        JobDescription jobDescription = new JobDescription();
        JobTitle jobTitle = jobTitleService.findEntityById(request.getJobTitleId());
        BeanUtils.copyProperties(request, jobDescription);
        jobDescription.setJobTitle(jobTitle);
        jobDescription.setCreatedAt(ZonedDateTime.now(ZoneOffset.UTC));
        jobDescription.setUpdatedAt(ZonedDateTime.now(ZoneOffset.UTC));
        jobDescription.setCreatedBy("system");
        jobDescription.setUpdatedBy("system");
        jobDescription.setIsActive(true);
        return convertToResponse(jobDescriptionRepository.save(jobDescription));
    }

    @Override
    public JobDescriptionResponse update(String id, CreateJobDescriptionRequest request) {
        JobDescription jobDescription = getEntityById(id);
        BeanUtils.copyProperties(request, jobDescription);
        return convertToResponse(jobDescriptionRepository.save(jobDescription));
    }

    @Override
    public JobDescriptionResponse getById(String id) {
        return convertToResponse(getEntityById(id));
    }

    @Override
    public List<JobDescriptionResponse> getByJobTitleId(String jobTitleId) {
        return jobDescriptionRepository.findByJobTitleId(jobTitleId).stream()
            .map(this::convertToResponse)
            .collect(Collectors.toList());
    }

    @Override
    public void delete(String id) {
        jobDescriptionRepository.deleteById(id);
    }

    private JobDescription getEntityById(String id) {
        return jobDescriptionRepository.findById(id).orElseThrow(() ->
             new ResponseStatusException(HttpStatus.NOT_FOUND, "Job Description Not Found"));
    }
}
