package com.lawencon.jobportal.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lawencon.jobportal.helper.ResponseHelper;
import com.lawencon.jobportal.model.request.CreateEducationRequest;
import com.lawencon.jobportal.model.response.WebResponse;
import com.lawencon.jobportal.service.EducationService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping({"/api/v1/educations"})
public class EducationController {
    private final EducationService educationService;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<?>>  saveEducation(@RequestBody CreateEducationRequest education) {
        return ResponseEntity.ok(ResponseHelper.ok(educationService.save(education)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<WebResponse<?>> findById(@PathVariable("id") String id){
        return ResponseEntity.ok(ResponseHelper.ok(educationService.getById(id)));
    }

    @GetMapping("/profile/{id}")
    public ResponseEntity<WebResponse<?>> getBUserId(@PathVariable("id") String id){
        return ResponseEntity.ok(ResponseHelper.ok(educationService.getAllByProfileId(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<WebResponse<?>> update(@PathVariable("id") String id, @RequestBody  CreateEducationRequest education) {
        return ResponseEntity.ok(ResponseHelper.ok(educationService.update(education, id)));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<WebResponse<?>> delete(@PathVariable("id") String id) {
        educationService.delete(id);
        return ResponseEntity.ok(ResponseHelper.ok("Education  deleted"));

    }
}
