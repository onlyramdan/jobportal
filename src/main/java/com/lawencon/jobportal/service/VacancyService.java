package com.lawencon.jobportal.service;

import java.util.List;
import com.lawencon.jobportal.model.request.CreateVacancyRequest;
import com.lawencon.jobportal.model.request.UpdateStatuVacancy;
import com.lawencon.jobportal.model.response.VacancyResponse;
import com.lawencon.jobportal.persistent.entity.StatusVacancyTrx;
import com.lawencon.jobportal.persistent.entity.Vacancy;

public interface VacancyService {
    VacancyResponse save(CreateVacancyRequest request);
    VacancyResponse update(String id, CreateVacancyRequest request);
    VacancyResponse getById(String id);
    List<VacancyResponse> getVacanciesByJobTitle(String jobTitleId);
    List<VacancyResponse> getVacanciesByEmployeeType(String employeeTypeId);
    List<VacancyResponse> getVacanciesByExperienceLevel(String experienceLevelId);
    List<VacancyResponse> getAll();
    void delete(String id);
    Vacancy getEntityById(String id);
    StatusVacancyTrx changeStatus(UpdateStatuVacancy  request);

}