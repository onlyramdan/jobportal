package com.lawencon.jobportal.controller;

import com.lawencon.jobportal.helper.ResponseHelper;
import com.lawencon.jobportal.model.request.CreateExperienceRequest;
import com.lawencon.jobportal.model.response.WebResponse;
import com.lawencon.jobportal.service.ExperienceService;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/experiences")
public class ExperienceController {
    private final ExperienceService experienceService;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<?>> saveExperience(@RequestBody CreateExperienceRequest request) {
        return ResponseEntity.ok(ResponseHelper.ok(experienceService.save(request)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<WebResponse<?>> getById(@PathVariable("id") String id) {
        return ResponseEntity.ok(ResponseHelper.ok(experienceService.getById(id)));
    }

    @GetMapping("/profile/{profileId}")
    public ResponseEntity<WebResponse<?>> getAllByProfileId(@PathVariable("profileId") String profileId) {
        return ResponseEntity.ok(ResponseHelper.ok(experienceService.getAllByProfileId(profileId)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<WebResponse<?>> updateExperience(@PathVariable("id") String id, @RequestBody CreateExperienceRequest request) {
        return ResponseEntity.ok(ResponseHelper.ok(experienceService.update(request, id)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<WebResponse<?>> deleteExperience(@PathVariable("id") String id) {
        experienceService.delete(id);
        return ResponseEntity.ok(ResponseHelper.ok("Experience deleted successfully"));
    }
}
