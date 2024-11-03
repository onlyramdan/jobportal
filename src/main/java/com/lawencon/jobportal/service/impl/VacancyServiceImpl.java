package com.lawencon.jobportal.service.impl;

import com.lawencon.jobportal.config.RabbitMQConfig;
import com.lawencon.jobportal.model.request.CreateVacancyRequest;
import com.lawencon.jobportal.model.request.CreateVacancyTrxRequest;
import com.lawencon.jobportal.model.request.NotificationRequest;
import com.lawencon.jobportal.model.request.UpdateStatuVacancy;
import com.lawencon.jobportal.model.response.VacancyResponse;
import com.lawencon.jobportal.persistent.entity.StatusVacancy;
import com.lawencon.jobportal.persistent.entity.StatusVacancyTrx;
import com.lawencon.jobportal.persistent.entity.Vacancy;
import com.lawencon.jobportal.persistent.repository.VacancyRepository;
import com.lawencon.jobportal.service.JobTitleService;
import com.lawencon.jobportal.service.LocationService;
import com.lawencon.jobportal.service.StatusVacancyService;
import com.lawencon.jobportal.service.StatusVacancyTrxService;
import com.lawencon.jobportal.service.EmployeeTypeService;
import com.lawencon.jobportal.service.ExperienceLevelService;
import com.lawencon.jobportal.service.VacancyService;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

import java.time.ZonedDateTime;
import java.time.ZoneOffset;

@AllArgsConstructor
@Transactional
@Service
public class VacancyServiceImpl implements VacancyService {

    private final VacancyRepository vacancyRepository;
    private final JobTitleService jobTitleService;
    private final LocationService locationService;
    private final EmployeeTypeService employeeTypeService;
    private final ExperienceLevelService experienceLevelService;
    private final StatusVacancyTrxService  statusVacancyTrxService;
    private final StatusVacancyService statusVacancyService;
    
    private final RabbitTemplate  rabbitTemplate;


    private VacancyResponse convertToResponse(Vacancy vacancy) {
        VacancyResponse response = new VacancyResponse();
        BeanUtils.copyProperties(vacancy, response);
        response.setJobTitleId(vacancy.getJobTitle().getName());
        response.setLocationId(vacancy.getLocation().getName());
        response.setEmployeeTypeId(vacancy.getEmployeeType().getName());
        response.setExperienceLevelId(vacancy.getExperienceLevel().getName());
        return response;
    }

    @Override
    public VacancyResponse save(CreateVacancyRequest request) {
        Vacancy vacancy = new Vacancy();
        vacancy.setCode(request.getCode());
        vacancy.setPicHrId(request.getPicHrId());
        vacancy.setJobTitle(jobTitleService.findEntityById(request.getJobTitleId()));
        vacancy.setLocation(locationService.findEntityById(request.getLocationId()));
        vacancy.setEmployeeType(employeeTypeService.findEntityById(request.getEmployeeTypeId()));
        vacancy.setExperienceLevel(experienceLevelService.findEntityById(request.getExperienceLevelId()));
        vacancy.setMinSalary(request.getMinSalary());
        vacancy.setMaxSalary(request.getMaxSalary());
        vacancy.setApplicationDeadline(request.getApplicationDeadline());
        vacancy.setOverview(request.getOverview());
        vacancy.setCreatedAt(ZonedDateTime.now(ZoneOffset.UTC));
        vacancy.setUpdatedAt(ZonedDateTime.now(ZoneOffset.UTC));
        vacancy.setCreatedBy("system");
        vacancy.setUpdatedBy("system");
        vacancy.setIsActive(true);
        Vacancy vacancySaved = vacancyRepository.save(vacancy);
        CreateVacancyTrxRequest trxRequest =  new CreateVacancyTrxRequest();  
        StatusVacancy statusVacancy = statusVacancyService.findByCode("PE");
        trxRequest.setStatusVacancy(statusVacancy);
        trxRequest.setVacancy(vacancySaved);
        statusVacancyTrxService.save(trxRequest);
        return convertToResponse(vacancySaved);
    }

    @Override
    public VacancyResponse update(String id, CreateVacancyRequest request) {
        Vacancy vacancy = getEntityById(id);
        BeanUtils.copyProperties(request, vacancy);
        return convertToResponse(vacancyRepository.save(vacancy));
    }

    @Override
    public VacancyResponse getById(String id) {
        return convertToResponse(getEntityById(id));
    }

    @Override
    public List<VacancyResponse> getVacanciesByJobTitle(String jobTitleId) {
        return vacancyRepository.findByJobTitleId(jobTitleId).stream()
            .map(this::convertToResponse)
            .collect(Collectors.toList());
    }

    @Override
    public List<VacancyResponse> getVacanciesByEmployeeType(String employeeTypeId) {
        return vacancyRepository.findByEmployeeTypeId(employeeTypeId).stream()
            .map(this::convertToResponse)
            .collect(Collectors.toList());
    }

    @Override
    public List<VacancyResponse> getVacanciesByExperienceLevel(String experienceLevelId) {
        return vacancyRepository.findByExperienceLevelId(experienceLevelId).stream()
            .map(this::convertToResponse)
            .collect(Collectors.toList());
    }

    @Override
    public void delete(String id) {
        vacancyRepository.deleteById(id);
    }

    @Override
    public Vacancy getEntityById(String id) {
        return vacancyRepository.findById(id).orElseThrow(() ->
             new ResponseStatusException(HttpStatus.NOT_FOUND, "Vacancy Not Found"));
    }

    @Override
    public List<VacancyResponse> getAll() {
        return vacancyRepository.findAll().stream().map(this::convertToResponse)
        .collect(Collectors.toList());
    }

    @Override
    public StatusVacancyTrx changeStatus(UpdateStatuVacancy request) {
        Vacancy vacancy = getEntityById(request.getVacancyId());
        StatusVacancy  statusVacancy = statusVacancyService.findEntityById(request.getStatusId());
        CreateVacancyTrxRequest trxRequest = new CreateVacancyTrxRequest();
        trxRequest.setStatusVacancy(statusVacancy);
        trxRequest.setVacancy(vacancy);
        StatusVacancyTrx vacancyTrx =  statusVacancyTrxService.save(trxRequest);

        if (vacancyTrx.getStatus().getCode().equals("ON")) {
            NotificationRequest notifRequest=  new NotificationRequest();
            notifRequest.setTitle("OPENED NEW Vacancy " +  vacancy.getJobTitle().getName());
            notifRequest.setMessage(vacancy.getOverview());
            notifRequest.setStatusVacancyTrxId(vacancyTrx.getId());
            rabbitTemplate.convertAndSend(RabbitMQConfig.VACANCY_NOTIFICATION_EXCHANGE,
                                      "vacancy.created.new", notifRequest);
        }
        return vacancyTrx;
    }
}
