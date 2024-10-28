package com.lawencon.jobportal.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.lawencon.jobportal.model.request.MasterRequest;
import com.lawencon.jobportal.model.request.UpdateMasterRequest;
import com.lawencon.jobportal.model.response.MasterResponse;
import com.lawencon.jobportal.persistent.entity.StatusVacancy;
import com.lawencon.jobportal.persistent.repository.StatusVacancyRepository;
import com.lawencon.jobportal.service.StatusVacancyService;

import lombok.AllArgsConstructor;

import java.time.ZonedDateTime;
import java.time.ZoneOffset;


@Service
@AllArgsConstructor
public class StatusVacancyServiceImpl implements StatusVacancyService {
    private final StatusVacancyRepository  statusVacancyRepository;

    private MasterResponse converterResponse(StatusVacancy  statusVacancy) {
        MasterResponse masterResponse = new MasterResponse();
        BeanUtils.copyProperties(statusVacancy, masterResponse);
        return masterResponse;
    }

    @Override
    public void delete(String id) {
        statusVacancyRepository.deleteById(id);
    }

    @Override
    public List<MasterResponse> findAll() {
        return statusVacancyRepository.findAll().stream().map(status->
        converterResponse(status)).collect(Collectors.toList());

    }

    @Override
    public MasterResponse findById(String id) {
        return converterResponse(findEntityById(id));

    }

    @Override
    public StatusVacancy findEntityById(String id) {
        return statusVacancyRepository.findById(id).orElseThrow(
            () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Status Vacancy Not Found"));
    }

    @Override
    public MasterResponse save(MasterRequest request) {
        StatusVacancy statusVacancy = new StatusVacancy();
        BeanUtils.copyProperties(request, statusVacancy);
        statusVacancy.setCreatedAt(ZonedDateTime.now(ZoneOffset.UTC));
        statusVacancy.setUpdatedAt(ZonedDateTime.now(ZoneOffset.UTC));
        statusVacancy.setCreatedBy("system");
        statusVacancy.setUpdatedBy("system");
        statusVacancy.setIsActive(true);
        return converterResponse(statusVacancyRepository.save(statusVacancy));
    }

    @Override
    public MasterResponse update(UpdateMasterRequest request) {
        StatusVacancy statusVacancy = findEntityById(request.getId());
        BeanUtils.copyProperties(request, statusVacancy);
        return converterResponse(statusVacancyRepository.save(statusVacancy)); 
    }
    
}
