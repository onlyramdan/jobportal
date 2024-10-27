package com.lawencon.jobportal.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.lawencon.jobportal.model.request.MasterRequest;
import com.lawencon.jobportal.model.request.UpdateMasterRequest;
import com.lawencon.jobportal.model.response.MasterResponse;
import com.lawencon.jobportal.persistent.entity.ExperienceLevel;
import com.lawencon.jobportal.persistent.repository.ExperienceLevelRepository;
import com.lawencon.jobportal.service.ExperienceLevelService;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;


import java.time.ZonedDateTime;
import java.time.ZoneOffset;

@Service
@AllArgsConstructor
@Transactional
public class ExperienceLevelServiceImpl implements  ExperienceLevelService {
    private final ExperienceLevelRepository  experienceLevelRepository;
    
    private MasterResponse converterResponse(ExperienceLevel  experienceLevel) {
        MasterResponse masterResponse = new MasterResponse();
        BeanUtils.copyProperties(experienceLevel, masterResponse);
        return masterResponse;
    }

    @Override
    public void delete(String id) {
        experienceLevelRepository.deleteById(id);
    }

    @Override
    public List<MasterResponse> findAll() {
        return experienceLevelRepository.findAll().stream().map(level->
        converterResponse(level)).collect(Collectors.toList());

    }

    @Override
    public MasterResponse findById(String id) {
        return converterResponse(findEntityById(id));

    }

    @Override
    public ExperienceLevel findEntityById(String id) {
        return experienceLevelRepository.findById(id).orElseThrow(
            () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Experience Level Not Found"));
    }

    @Override
    public MasterResponse save(MasterRequest request) {
        ExperienceLevel experienceLevel = new ExperienceLevel();
        BeanUtils.copyProperties(request, experienceLevel);
        experienceLevel.setCreatedAt(ZonedDateTime.now(ZoneOffset.UTC));
        experienceLevel.setUpdatedAt(ZonedDateTime.now(ZoneOffset.UTC));
        experienceLevel.setCreatedBy("system");
        experienceLevel.setUpdatedBy("system");
        experienceLevel.setIsActive(true);
        return converterResponse(experienceLevelRepository.save(experienceLevel));
    }

    @Override
    public MasterResponse update(UpdateMasterRequest request) {
        ExperienceLevel experienceLevel = findEntityById(request.getId());
        BeanUtils.copyProperties(request, experienceLevel);
        return converterResponse(experienceLevelRepository.save(experienceLevel)); 
    }
}
