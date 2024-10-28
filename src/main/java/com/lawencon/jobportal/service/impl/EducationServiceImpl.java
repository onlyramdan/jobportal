package com.lawencon.jobportal.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.lawencon.jobportal.model.request.CreateEducationRequest;
import com.lawencon.jobportal.model.response.EducationResponse;
import com.lawencon.jobportal.persistent.entity.Education;
import com.lawencon.jobportal.persistent.entity.EducationLevel;
import com.lawencon.jobportal.persistent.entity.Profile;
import com.lawencon.jobportal.persistent.repository.EducationRepository;
import com.lawencon.jobportal.service.EducationLevelService;
import com.lawencon.jobportal.service.EducationService;
import com.lawencon.jobportal.service.ProfileService;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;


import java.time.ZonedDateTime;
import java.time.ZoneOffset;


@AllArgsConstructor
@Transactional
@Service
public class EducationServiceImpl implements EducationService {
    private final EducationRepository  educationRepository;
    private final EducationLevelService  educationLevelService;
    private final ProfileService  profileService;

    private EducationResponse converterResponse(Education  education) {
        EducationResponse response = new EducationResponse();
        BeanUtils.copyProperties(education, response);
        response.setEducation(education.getEducationLevel().getName());;
        response.setProfileId(education.getProfile().getId());
        return response;
    }


    @Override
    public void delete(String id) {
        educationRepository.deleteById(id);
    }

    @Override
    public List<EducationResponse> getAllByProfileId(String id) {
        return educationRepository.findByProfileId(id).stream().map(edu->
        converterResponse(edu)).collect(Collectors.toList());
    }

    @Override
    public EducationResponse getById(String id) {
        return converterResponse(getEntityById(id));

    }

    @Override
    public EducationResponse save(CreateEducationRequest request) {
        Education education = new Education();
        BeanUtils.copyProperties(request, education);
        EducationLevel level   = educationLevelService.findEntityById(request.getEducationLevelId());
        Profile profile = profileService.findEntityById(request.getProfileId());
        education.setProfile(profile);
        education.setEducationLevel(level);
        education.setCreatedAt(ZonedDateTime.now(ZoneOffset.UTC));
        education.setUpdatedAt(ZonedDateTime.now(ZoneOffset.UTC));
        education.setCreatedBy("system");
        education.setUpdatedBy("system");
        education.setIsActive(true);
        return converterResponse(educationRepository.save(education));
    }

    @Override
    public EducationResponse update(CreateEducationRequest request, String id) {
        Education education = getEntityById(id);
        BeanUtils.copyProperties(request, education);
        return converterResponse(educationRepository.save(education));

    }


    @Override
    public Education getEntityById(String id) {
        return educationRepository.findById(id).orElseThrow(
            () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Education Not Found")
        );
    }
}
