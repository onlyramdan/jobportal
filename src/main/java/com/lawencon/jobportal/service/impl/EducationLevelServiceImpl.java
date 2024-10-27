package com.lawencon.jobportal.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.lawencon.jobportal.model.request.MasterRequest;
import com.lawencon.jobportal.model.request.UpdateMasterRequest;
import com.lawencon.jobportal.model.response.MasterResponse;
import com.lawencon.jobportal.persistent.entity.EducationLevel;
import com.lawencon.jobportal.persistent.repository.EducationLevelRepository;
import com.lawencon.jobportal.service.EducationLevelService;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

import java.util.stream.Collectors;

import java.time.ZonedDateTime;
import java.time.ZoneOffset;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class EducationLevelServiceImpl implements  EducationLevelService {
    private final EducationLevelRepository  educationLevelRepository;
    
    private MasterResponse converterResponse(EducationLevel  educationLevel) {
        MasterResponse masterResponse = new MasterResponse();
        BeanUtils.copyProperties(educationLevel, masterResponse);
        return masterResponse;
    }


    @Override
    public MasterResponse save(MasterRequest request) {
        EducationLevel educationLevel = new EducationLevel();
        BeanUtils.copyProperties(request, educationLevel);
        educationLevel.setCreatedAt(ZonedDateTime.now(ZoneOffset.UTC));
        educationLevel.setUpdatedAt(ZonedDateTime.now(ZoneOffset.UTC));
        educationLevel.setCreatedBy("system");
        educationLevel.setUpdatedBy("system");
        educationLevel.setIsActive(true);
        return converterResponse(educationLevelRepository.save(educationLevel));
    }

    @Override
    public List<MasterResponse> findAll() {
        return educationLevelRepository.findAll().stream()
        .map(this::converterResponse)
        .collect(Collectors.toList());
    }

    @Override
    public MasterResponse findById(String id) {
        return converterResponse(findEntityById(id));
    }

    @Override
    public EducationLevel findEntityById(String id) {
        return educationLevelRepository.findById(id).orElseThrow(
            () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Education Level Not Found")
        );
    }

    @Override
    public MasterResponse update(UpdateMasterRequest request) {
        EducationLevel educationLevel = findEntityById(request.getId());
        BeanUtils.copyProperties(request, educationLevel);
        return converterResponse(educationLevelRepository.save(educationLevel));
    }


    @Override
    public void delete(String id) {
        educationLevelRepository.deleteById(id);
    }
}
