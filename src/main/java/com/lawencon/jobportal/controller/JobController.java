package com.lawencon.jobportal.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lawencon.jobportal.helper.ResponseHelper;
import com.lawencon.jobportal.model.request.CreateJobDescriptionRequest;
import com.lawencon.jobportal.model.request.CreateJobRequest;
import com.lawencon.jobportal.model.request.CreateJobSpecificationsRequest;
import com.lawencon.jobportal.model.request.PagingRequest;
import com.lawencon.jobportal.model.response.JobResponse;
import com.lawencon.jobportal.model.response.MasterResponse;
import com.lawencon.jobportal.model.response.WebResponse;
import com.lawencon.jobportal.service.JobDescriptionService;
import com.lawencon.jobportal.service.JobSpecificationService;
import com.lawencon.jobportal.service.JobTitleService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping({ "/api/v1/jobs" })
public class JobController {
    private final JobTitleService jobTitleService;
    private final JobDescriptionService jobDescriptionService;
    private final JobSpecificationService jobSpecificationService;

    @GetMapping()
    public ResponseEntity<WebResponse<List<MasterResponse>>> getJobTitle(
            PagingRequest pagingRequest,
            @RequestParam(required = false) String inquiry) {
        return ResponseEntity.ok(ResponseHelper.ok(pagingRequest, jobTitleService.findAll(pagingRequest, inquiry)));
    }

    @PostMapping()
    public ResponseEntity<WebResponse<String>> createJob(@RequestBody CreateJobRequest createJobRequest) {
        MasterResponse jobTitle = jobTitleService.save(createJobRequest.getJobTitle());
        List<CreateJobDescriptionRequest> requestDesc = createJobRequest.getJobDesc().stream().map(desc -> {
            CreateJobDescriptionRequest request = new CreateJobDescriptionRequest();
            request.setJobTitleId(jobTitle.getId());
            request.setDescription(desc.getJobDesc());
            request.setTitleDesc(desc.getJobTitle());
            return request;
        }).toList();
        jobDescriptionService.saveAll(requestDesc);
        CreateJobSpecificationsRequest specRequest = new CreateJobSpecificationsRequest();
        specRequest.setJobTitleId(jobTitle.getId());
        specRequest.setSpecifications(createJobRequest.getJobSpec());
        jobSpecificationService.save(specRequest);
        return ResponseEntity.ok(ResponseHelper.ok("Job Successfully Created"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<WebResponse<JobResponse>> getJobTitleById(@PathVariable String id) {
        JobResponse jobResponse = new JobResponse();
        jobResponse.setJobTitle(jobTitleService.findById(id));
        jobResponse.setJobSpecList(jobSpecificationService.getAllByJobTitleId(id));
        jobResponse.setJobDescList(jobDescriptionService.getByJobTitleId(id));
        return ResponseEntity.ok(ResponseHelper.ok(jobResponse));
    }
}
