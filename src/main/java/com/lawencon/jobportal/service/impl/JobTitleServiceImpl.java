package com.lawencon.jobportal.service.impl;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.lawencon.jobportal.helper.SpecificationHelper;
import com.lawencon.jobportal.model.request.PagingRequest;
import com.lawencon.jobportal.model.request.UpdateMasterRequest;
import com.lawencon.jobportal.model.response.MasterResponse;
import com.lawencon.jobportal.persistent.entity.JobTitle;
import com.lawencon.jobportal.persistent.repository.JobDescriptionRepository;
import com.lawencon.jobportal.persistent.repository.JobSpecificationRepository;
import com.lawencon.jobportal.persistent.repository.JobTitleRepository;
import com.lawencon.jobportal.persistent.repository.VacancyRepository;
import com.lawencon.jobportal.service.JobTitleService;
import com.lawencon.jobportal.util.GenerateCode;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@Transactional
@AllArgsConstructor
public class JobTitleServiceImpl implements JobTitleService {
    private final VacancyRepository vacancyRepository;
    private final JobDescriptionRepository jobDescriptionRepository;
    private final JobSpecificationRepository jobSpecificationRepository;
    private final JobTitleRepository jobTitleRepository;

    private MasterResponse converterResponse(JobTitle jobTitle) {
        MasterResponse masterResponse = new MasterResponse();
        BeanUtils.copyProperties(jobTitle, masterResponse);
        return masterResponse;
    }

    @Override
    public void delete(String id) {
        if (!jobTitleRepository.existsById(id)) {
            throw new ResponseStatusException( HttpStatus.BAD_REQUEST,"Job Title with ID " + id + " does not exist.");
        }
        if (vacancyRepository.existsByJobTitleId(id)) {
            throw new ResponseStatusException( HttpStatus.BAD_REQUEST,"Cannot delete. Job Title is being used in Vacancy.");
        }
        if (jobDescriptionRepository.existsByJobTitleId(id)) {
            throw new ResponseStatusException( HttpStatus.BAD_REQUEST, "Cannot delete. Job Title is being used in Job Description.");
        }
        if (jobSpecificationRepository.existsByJobTitleId(id)) {
            throw new ResponseStatusException( HttpStatus.BAD_REQUEST, "Cannot delete. Job Title is being used in Job Specification.");
        }
        jobTitleRepository.deleteById(id);
    }

    @Override
    public List<MasterResponse> findAll() {
        return jobTitleRepository.findAll().stream().map(job -> converterResponse(job)).collect(Collectors.toList());

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
    public MasterResponse save(String request) {
        JobTitle jobTitle = new JobTitle();
        String code = "JOB-" + GenerateCode.generateCode(3);
        jobTitle.setCode(code);
        jobTitle.setName(request);
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

    @Override
    public Page<MasterResponse> findAll(PagingRequest pagingRequest, String inquiry) {
        PageRequest pageRequest = PageRequest.of(pagingRequest.getPage(), pagingRequest.getPageSize(),
                SpecificationHelper.createSort(pagingRequest.getSortBy()));
        Specification<JobTitle> spec = Specification.where(null);
        if (inquiry != null) {
            spec = spec.and(SpecificationHelper.inquiryFilter(Arrays.asList("name", "code"), inquiry));
        }
        Page<JobTitle> jobTitleResponse = jobTitleRepository.findAll(spec, pageRequest);
        List<MasterResponse> responses = jobTitleResponse.getContent().stream().map(jobTitle -> {
            MasterResponse response = new MasterResponse();
            BeanUtils.copyProperties(jobTitle, response);
            return response;
        }).toList();
        return new PageImpl<>(responses, pageRequest, jobTitleResponse.getTotalElements());
    }

    @Override
    public Long countAll() {
        return jobTitleRepository.count();
    }
}
