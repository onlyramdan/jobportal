package com.lawencon.jobportal.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lawencon.jobportal.helper.ResponseHelper;
import com.lawencon.jobportal.model.request.ApplicationRequest;
import com.lawencon.jobportal.model.response.WebResponse;
import com.lawencon.jobportal.service.ApplicationService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping({ "/api/v1/applications" })
public class ApplicationController {
    private final ApplicationService applicationService;

    @PostMapping("/apply")
    public ResponseEntity<WebResponse<String>> apply(@RequestBody ApplicationRequest request) {
        applicationService.createApplication(request);
        return ResponseEntity.ok(ResponseHelper.ok("Successfully Applied"));
    }

    @GetMapping("/{id}/apply")
    public ResponseEntity<WebResponse<?>> getApplication(@PathVariable String id) {
        return ResponseEntity.ok(ResponseHelper.ok(applicationService.getApplicationById(id)));
    }

}
