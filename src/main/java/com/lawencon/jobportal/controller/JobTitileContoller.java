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
import com.lawencon.jobportal.model.request.MasterRequest;
import com.lawencon.jobportal.model.request.UpdateMasterRequest;
import com.lawencon.jobportal.model.response.WebResponse;
import com.lawencon.jobportal.service.JobTitleService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping({"/api/v1/job-title"})
public class JobTitileContoller {
    private  final JobTitleService jobTitleService;


    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<?>> save(@RequestBody  MasterRequest request) {
        return ResponseEntity.ok(ResponseHelper.ok(jobTitleService.save(request)));
    }

    @GetMapping
    public ResponseEntity<WebResponse<?>> findAll() {
        return ResponseEntity.ok(ResponseHelper.ok(jobTitleService.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<WebResponse<?>> findById(@PathVariable("id") String id) {
        return ResponseEntity.ok(ResponseHelper.ok(jobTitleService.findById(id)));
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<?>> update(@RequestBody UpdateMasterRequest request){
        return ResponseEntity.ok(ResponseHelper.ok(jobTitleService.update(request)));
    }

    @DeleteMapping("/{id}")
    public  ResponseEntity<WebResponse<?>> delete(@PathVariable("id") String id) {
        jobTitleService.delete(id);
        return ResponseEntity.ok(ResponseHelper.ok());
    }    
}