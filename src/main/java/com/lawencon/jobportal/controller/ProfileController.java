package com.lawencon.jobportal.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lawencon.jobportal.helper.ResponseHelper;
import com.lawencon.jobportal.model.request.CreateProfileRequest;
import com.lawencon.jobportal.model.response.WebResponse;
import com.lawencon.jobportal.service.ProfileService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping({ "/api/v1/profiles" })
public class ProfileController {
    private final ProfileService profileService;

    @GetMapping("/{id}")
    public ResponseEntity<WebResponse<?>> findById(@PathVariable("id") String id) {
        return ResponseEntity.ok(ResponseHelper.ok(profileService.findById(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<WebResponse<?>> update(@RequestBody CreateProfileRequest request,
            @PathVariable("id") String id) {
        return ResponseEntity.ok(ResponseHelper.ok(profileService.update(request, id)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<WebResponse<?>> delete(@PathVariable("id") String id) {
        profileService.delete(id);
        return ResponseEntity.ok(ResponseHelper.ok("Profile deleted"));

    }

    @GetMapping("/user/{id}")
    public ResponseEntity<WebResponse<?>> findByUserId(@PathVariable("id") String id) {
        return ResponseEntity.ok(ResponseHelper.ok(profileService.findByUserId(id)));
    }

    @GetMapping("{id}/check")
    public ResponseEntity<WebResponse<?>> checkProfile(@PathVariable("id") String id) {
        return ResponseEntity.ok(ResponseHelper.ok(profileService.checkProfileExist(id)));
    }
}
