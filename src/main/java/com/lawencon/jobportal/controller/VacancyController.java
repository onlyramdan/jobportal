package com.lawencon.jobportal.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.lawencon.jobportal.helper.ResponseHelper;
import com.lawencon.jobportal.model.request.CreateVacancyRequest;
import com.lawencon.jobportal.model.request.CreateVacancyTrxRequest;
import com.lawencon.jobportal.model.request.UpdateStatuVacancy;
import com.lawencon.jobportal.model.response.VacancyResponse;
import com.lawencon.jobportal.model.response.WebResponse;
import com.lawencon.jobportal.persistent.entity.StatusVacancy;
import com.lawencon.jobportal.persistent.entity.Vacancy;
import com.lawencon.jobportal.service.StatusVacancyService;
import com.lawencon.jobportal.service.StatusVacancyTrxService;
import com.lawencon.jobportal.service.VacancyService;

import lombok.AllArgsConstructor;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/vacancies")
public class VacancyController {
    private final VacancyService vacancyService;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<VacancyResponse>> createVacancy(@RequestBody CreateVacancyRequest request) {
        VacancyResponse response = vacancyService.save(request);
        return ResponseEntity.ok(ResponseHelper.ok(response));
    }

    @GetMapping("/{id}")
    public ResponseEntity<WebResponse<VacancyResponse>> getVacancyById(@PathVariable String id) {
        VacancyResponse response = vacancyService.getById(id);
        return ResponseEntity.ok(ResponseHelper.ok(response));
    }

    @PutMapping("/{id}")
    public ResponseEntity<WebResponse<VacancyResponse>> updateVacancy(@PathVariable String id, @RequestBody CreateVacancyRequest request) {
        VacancyResponse response = vacancyService.update(id, request);
        return ResponseEntity.ok(ResponseHelper.ok(response));
    }

    @GetMapping
    public ResponseEntity<WebResponse<List<VacancyResponse>>> getAllVacancies() {
        List<VacancyResponse> responses = vacancyService.getAll();
        return ResponseEntity.ok(ResponseHelper.ok(responses));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<WebResponse<Void>> deleteVacancy(@PathVariable String id) {
        vacancyService.delete(id);
        return ResponseEntity.ok(ResponseHelper.ok());
    }

    @GetMapping("/jobtitle/{jobTitleId}")
    public ResponseEntity<WebResponse<List<VacancyResponse>>> getVacanciesByJobTitle(@PathVariable String jobTitleId) {
        List<VacancyResponse> responses = vacancyService.getVacanciesByJobTitle(jobTitleId);
        return ResponseEntity.ok(ResponseHelper.ok(responses));
    }

    @GetMapping("/employeetype/{employeeTypeId}")
    public ResponseEntity<WebResponse<List<VacancyResponse>>> getVacanciesByEmployeeType(@PathVariable String employeeTypeId) {
        List<VacancyResponse> responses = vacancyService.getVacanciesByEmployeeType(employeeTypeId);
        return ResponseEntity.ok(ResponseHelper.ok(responses));
    }

    @GetMapping("/experiencelevel/{experienceLevelId}")
    public ResponseEntity<WebResponse<List<VacancyResponse>>> getVacanciesByExperienceLevel(@PathVariable String experienceLevelId) {
        List<VacancyResponse> responses = vacancyService.getVacanciesByExperienceLevel(experienceLevelId);
        return ResponseEntity.ok(ResponseHelper.ok(responses));
    }

    @PostMapping("/changestatus")
    public ResponseEntity<WebResponse<?>> changeStatus(@RequestBody UpdateStatuVacancy request) {
        return ResponseEntity.ok(ResponseHelper.ok(vacancyService.changeStatus(request)));
    }

}
