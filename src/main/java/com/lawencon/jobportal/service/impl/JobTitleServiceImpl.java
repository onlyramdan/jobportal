package com.lawencon.jobportal.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.lawencon.jobportal.model.request.MasterRequest;
import com.lawencon.jobportal.model.request.UpdateMasterRequest;
import com.lawencon.jobportal.model.response.MasterResponse;
import com.lawencon.jobportal.persistent.entity.JobTitle;
import com.lawencon.jobportal.persistent.repository.JobTitleRepository;
import com.lawencon.jobportal.service.JobTitleService;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;


import java.util.List;
import java.util.stream.Collectors;


import java.time.ZonedDateTime;
import java.time.ZoneOffset;

@Service
@Transactional
@AllArgsConstructor
public class JobTitleServiceImpl implements JobTitleService {
    private final JobTitleRepository  jobTitleRepository;

    private MasterResponse converterResponse(JobTitle  jobTitle) {
        MasterResponse masterResponse = new MasterResponse();
        BeanUtils.copyProperties(jobTitle, masterResponse);
        return masterResponse;
    }

    @Override
    public void delete(String id) {
        jobTitleRepository.deleteById(id);
    }

    @Override
    public List<MasterResponse> findAll() {
        return jobTitleRepository.findAll().stream().map(job->
        converterResponse(job)).collect(Collectors.toList());

    }

    @Override
    public MasterResponse findById(String id) {
        return converterResponse(findEntityById(id));

    }

    @Override
    public JobTitle findEntityById(String id) {
        return jobTitleRepository.findById(id).orElseThrow(
            () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Experience Level Not Found"));
    }

    @Override
    public MasterResponse save(MasterRequest request) {
        JobTitle jobTitle = new JobTitle();
        BeanUtils.copyProperties(request, jobTitle);
        jobTitle.setCreatedAt(ZonedDateTime.now(ZoneOffset.UTC));
        jobTitle.setUpdatedAt(ZonedDateTime.now(ZoneOffset.UTC));
        jobTitle.setCreatedBy("system");
        jobTitle.setUpdatedBy("system");
        jobTitle.setIsActive(true);
        return converterResponse(jobTitleRepository.save(jobTitle));
    }

    @Override
    public MasterResponse update(UpdateMasterRequest request) {
        JobTitle jobTitle = findEntityById(request.getId());
        BeanUtils.copyProperties(request, jobTitle);
        return converterResponse(jobTitleRepository.save(jobTitle)); 
    }
}
