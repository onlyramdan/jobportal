package com.lawencon.jobportal.controller;

import com.lawencon.jobportal.helper.ResponseHelper;
import com.lawencon.jobportal.model.request.CreateJobDescriptionRequest;
import com.lawencon.jobportal.model.request.UpdateDecscriptionRequest;
import com.lawencon.jobportal.model.response.JobDescriptionResponse;
import com.lawencon.jobportal.model.response.WebResponse;
import com.lawencon.jobportal.service.JobDescriptionService;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.AllArgsConstructor;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/job-descriptions")
public class JobDescriptionController {

    private final JobDescriptionService jobDescriptionService;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<?>> save(@RequestBody CreateJobDescriptionRequest request) {
        return ResponseEntity.ok(ResponseHelper.ok(jobDescriptionService.save(request)));
    }

    @PutMapping()
    public ResponseEntity<WebResponse<String>> update(@RequestBody UpdateDecscriptionRequest request) {
        jobDescriptionService.update(request);
        return ResponseEntity.ok(ResponseHelper.ok( "Job Description Updated Successfully"));
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<?>> getById(@PathVariable("id") String id) {
        return ResponseEntity.ok(ResponseHelper.ok(jobDescriptionService.getById(id)));
    }

    @GetMapping(value = "/job-title/{jobTitleId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<?>> getByJobTitleId(@PathVariable("jobTitleId") String jobTitleId) {
        List<JobDescriptionResponse> descriptions = jobDescriptionService.getByJobTitleId(jobTitleId);
        return ResponseEntity.ok(ResponseHelper.ok(descriptions));
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<?>> delete(@PathVariable("id") String id) {
        jobDescriptionService.delete(id);
        return ResponseEntity.ok(ResponseHelper.ok("Job Description deleted successfully"));
    }
}
