package com.lawencon.jobportal.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.lawencon.jobportal.config.RabbitMQConfig;
import com.lawencon.jobportal.helper.SpecificationHelper;
import com.lawencon.jobportal.model.request.CreateVacancyRequest;
import com.lawencon.jobportal.model.request.CreateVacancyTrxRequest;
import com.lawencon.jobportal.model.request.NotificationRequest;
import com.lawencon.jobportal.model.request.PagingRequest;
import com.lawencon.jobportal.model.request.UpdateStatusVacancy;
import com.lawencon.jobportal.model.response.JobDescriptionResponse;
import com.lawencon.jobportal.model.response.JobSpecificationResponse;
import com.lawencon.jobportal.model.response.VacancyDetailResponse;
import com.lawencon.jobportal.model.response.VacancyResponse;
import com.lawencon.jobportal.persistent.entity.EmployeeType;
import com.lawencon.jobportal.persistent.entity.ExperienceLevel;
import com.lawencon.jobportal.persistent.entity.JobTitle;
import com.lawencon.jobportal.persistent.entity.Location;
import com.lawencon.jobportal.persistent.entity.StatusVacancy;
import com.lawencon.jobportal.persistent.entity.StatusVacancyTrx;
import com.lawencon.jobportal.persistent.entity.Vacancy;
import com.lawencon.jobportal.persistent.repository.VacancyRepository;
import com.lawencon.jobportal.service.EmployeeTypeService;
import com.lawencon.jobportal.service.ExperienceLevelService;
import com.lawencon.jobportal.service.JobDescriptionService;
import com.lawencon.jobportal.service.JobSpecificationService;
import com.lawencon.jobportal.service.JobTitleService;
import com.lawencon.jobportal.service.LocationService;
import com.lawencon.jobportal.service.StatusVacancyService;
import com.lawencon.jobportal.service.StatusVacancyTrxService;
import com.lawencon.jobportal.service.VacancyService;
import com.lawencon.jobportal.util.GenerateCode;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Transactional
@Service
public class VacancyServiceImpl implements VacancyService {
    private final VacancyRepository vacancyRepository;
    private final JobTitleService jobTitleService;
    private final JobSpecificationService jobSpecificationService;
    private final JobDescriptionService jobDescriptionService;
    private final LocationService locationService;
    private final EmployeeTypeService employeeTypeService;
    private final ExperienceLevelService experienceLevelService;
    private final StatusVacancyTrxService statusVacancyTrxService;
    private final StatusVacancyService statusVacancyService;
    private final RabbitTemplate rabbitTemplate;

    private VacancyResponse convertToResponse(Vacancy vacancy) {
        VacancyResponse response = new VacancyResponse();
        BeanUtils.copyProperties(vacancy, response);
        response.setJobTitleId(vacancy.getJobTitle().getId());
        response.setEmployeeTypeId(vacancy.getEmployeeType().getId());
        response.setExperienceLevelId(vacancy.getExperienceLevel().getId());
        response.setLocationId(vacancy.getLocation().getId());
        response.setApplicationDeadline(vacancy.getApplicationDeadline().toString());
        response.setJobTitle(vacancy.getJobTitle().getName());
        response.setLocation(vacancy.getLocation().getName());
        response.setEmployeeType(vacancy.getEmployeeType().getName());
        response.setExperienceLevel(vacancy.getExperienceLevel().getName());
        StatusVacancyTrx statusTrx = statusVacancyTrxService.findByVacancyId(vacancy.getId());
        response.setStatus(statusTrx.getStatus().getName());
        return response;
    }

    @Override
    public VacancyResponse save(CreateVacancyRequest request) {
        Vacancy vacancy = new Vacancy();
        vacancy.setCode("VAC-" + GenerateCode.generateCode(3));
        vacancy.setPicHrId(request.getPicHrId());
        vacancy.setJobTitle(jobTitleService.findEntityById(request.getJobTitleId()));
        vacancy.setLocation(locationService.findEntityById(request.getLocationId()));
        vacancy.setEmployeeType(employeeTypeService.findEntityById(request.getEmployeeTypeId()));
        vacancy.setExperienceLevel(experienceLevelService.findEntityById(request.getExperienceLevelId()));
        vacancy.setMinSalary(request.getMinSalary());
        vacancy.setMaxSalary(request.getMaxSalary());
        vacancy.setApplicationDeadline(request.getApplicationDeadline());
        vacancy.setOverview(request.getOverview());
        vacancy.setIsActive(true);
        Vacancy vacancySaved = vacancyRepository.save(vacancy);
        CreateVacancyTrxRequest trxRequest = new CreateVacancyTrxRequest();
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
        JobTitle jobTitle = jobTitleService.findEntityById(request.getJobTitleId());
        vacancy.setJobTitle(jobTitle);
        Location location = locationService.findEntityById(request.getLocationId());
        vacancy.setLocation(location);
        ExperienceLevel experienceLevel = experienceLevelService.findEntityById(request.getExperienceLevelId());
        vacancy.setExperienceLevel(experienceLevel);
        EmployeeType employeeType = employeeTypeService.findEntityById(request.getEmployeeTypeId());
        vacancy.setEmployeeType(employeeType);
        return convertToResponse(vacancyRepository.save(vacancy));
    }

    @Override
    public VacancyDetailResponse getById(String id) {
        VacancyDetailResponse responseDetail = new VacancyDetailResponse();
        Vacancy vacancy = getEntityById(id);
        VacancyResponse response = convertToResponse(vacancy);
        responseDetail.setVacancyResponse(response);
        List<JobSpecificationResponse> specificationResponses = jobSpecificationService
                .getAllByJobTitleId(vacancy.getJobTitle().getId());
        List<JobDescriptionResponse> descriptionResponses = jobDescriptionService
                .getByJobTitleId(vacancy.getJobTitle().getId());
        responseDetail.setSpecifications(specificationResponses);
        responseDetail.setJobDescriptions(descriptionResponses);
        return responseDetail;
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
        return vacancyRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Vacancy Not Found"));
    }

    @Override
    public List<VacancyResponse> getAll() {
        return vacancyRepository.findAll().stream().map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public StatusVacancyTrx changeStatus(UpdateStatusVacancy request) {
        Vacancy vacancy = getEntityById(request.getVacancyId());
        StatusVacancy statusVacancy = statusVacancyService.findEntityById(request.getStatusId());
        CreateVacancyTrxRequest trxRequest = new CreateVacancyTrxRequest();
        trxRequest.setStatusVacancy(statusVacancy);
        trxRequest.setVacancy(vacancy);
        StatusVacancyTrx vacancyTrx = statusVacancyTrxService.save(trxRequest);

        if (vacancyTrx.getStatus().getCode().equals("ON")) {
            NotificationRequest notifRequest = new NotificationRequest();
            notifRequest.setTitle("OPENED NEW Vacancy " + vacancy.getJobTitle().getName());
            notifRequest.setMessage(vacancy.getOverview());
            notifRequest.setStatusVacancyTrxId(vacancyTrx.getId());
            rabbitTemplate.convertAndSend(RabbitMQConfig.VACANCY_NOTIFICATION_EXCHANGE,
                    "vacancy.created.new", notifRequest);
        }
        return vacancyTrx;
    }

    @Override
    public Page<VacancyResponse> findAllPage(PagingRequest pagingRequest, String inquiry) {
        PageRequest pageRequest = PageRequest.of(pagingRequest.getPage(), pagingRequest.getPageSize(),
                SpecificationHelper.createSort(pagingRequest.getSortBy()));
        Specification<Vacancy> spec = Specification.where(null);
        if (inquiry != null) {
            spec = spec.and(SpecificationHelper.inquiryFilter(Arrays.asList("jobTitle.name", "code","location.name"), inquiry));
        }

        Page<Vacancy> locationResponse = vacancyRepository.findAll(spec, pageRequest);
        List<VacancyResponse> responses = locationResponse.getContent().stream().map(vacancy -> {
            VacancyResponse response = new VacancyResponse();
            response = convertToResponse(vacancy);
            return response;
        }).toList();
        return new PageImpl<>(responses, pageRequest, locationResponse.getTotalElements());

    }

    @Override
    public Long countAll() {
        return vacancyRepository.count();
    }

}