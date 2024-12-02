package com.lawencon.jobportal.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.lawencon.jobportal.helper.ResponseHelper;
import com.lawencon.jobportal.model.request.CreateJobSpecificationsRequest;
import com.lawencon.jobportal.model.request.UpdateSpecRequest;
import com.lawencon.jobportal.model.response.WebResponse;
import com.lawencon.jobportal.service.JobSpecificationService;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/job-specifications")
public class JobSpecificationController {

    private final JobSpecificationService jobSpecificationService;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<?>> saveJobSpecifications(@RequestBody CreateJobSpecificationsRequest request) {
        return ResponseEntity.ok(ResponseHelper.ok(jobSpecificationService.save(request)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<WebResponse<?>> getJobSpecificationById(@PathVariable("id") String id) {
        return ResponseEntity.ok(ResponseHelper.ok(jobSpecificationService.getById(id)));
    }

    @GetMapping("/job-title/{id}")
    public ResponseEntity<WebResponse<?>> getJobSpecificationsByJobTitleId(@PathVariable("id") String jobTitleId) {
        return ResponseEntity.ok(ResponseHelper.ok(jobSpecificationService.getAllByJobTitleId(jobTitleId)));
    }

    @PutMapping("/job-title/{id}")
    public ResponseEntity<WebResponse<?>> updateJobSpecificationAll(@PathVariable("id") String id, @RequestBody CreateJobSpecificationsRequest request) {
        return ResponseEntity.ok(ResponseHelper.ok(jobSpecificationService.updateAll(id,request)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<WebResponse<?>> updateJobSpecification(@PathVariable("id") String id, @RequestBody CreateJobSpecificationsRequest request) {
        return ResponseEntity.ok(ResponseHelper.ok(jobSpecificationService.update(id,request)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<WebResponse<?>> deleteJobSpecification(@PathVariable("id") String id) {
        jobSpecificationService.delete(id);
        return ResponseEntity.ok(ResponseHelper.ok("Job Specification deleted successfully"));
    }

    @PutMapping("/update")
    public ResponseEntity<WebResponse<String>> updateJobSpecification(@RequestBody UpdateSpecRequest request) {
        jobSpecificationService.updateSpec(request);
        return ResponseEntity.ok(ResponseHelper.ok(
            "Job Specification updated successfully"
        ));
    }
}
