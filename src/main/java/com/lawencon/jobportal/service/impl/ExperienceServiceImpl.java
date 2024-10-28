package com.lawencon.jobportal.service.impl;

import com.lawencon.jobportal.model.request.CreateExperienceRequest;
import com.lawencon.jobportal.model.response.ExperienceResponse;
import com.lawencon.jobportal.persistent.entity.Experience;
import com.lawencon.jobportal.persistent.entity.ExperienceLevel;
import com.lawencon.jobportal.persistent.entity.Profile;
import com.lawencon.jobportal.persistent.repository.ExperienceRepository;
import com.lawencon.jobportal.service.ExperienceLevelService;
import com.lawencon.jobportal.service.ExperienceService;
import com.lawencon.jobportal.service.ProfileService;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Transactional
@Service
public class ExperienceServiceImpl implements ExperienceService {
    private final ExperienceRepository experienceRepository;
    private final ExperienceLevelService experienceLevelService;
    private final ProfileService profileService;

    private ExperienceResponse convertToResponse(Experience experience) {
        ExperienceResponse response = new ExperienceResponse();
        BeanUtils.copyProperties(experience, response);
        response.setExperienceLevel(experience.getExperienceLevel().getName());
        return response;
    }

    @Override
    public ExperienceResponse save(CreateExperienceRequest request) {
        Experience experience = new Experience();
        BeanUtils.copyProperties(request, experience);

        Profile profile = profileService.findEntityById(request.getProfileId());
        ExperienceLevel experienceLevel = experienceLevelService.findEntityById(request.getExperienceLevelId());

        experience.setProfile(profile);
        experience.setExperienceLevel(experienceLevel);

        return convertToResponse(experienceRepository.save(experience));
    }

    @Override
    public ExperienceResponse update(CreateExperienceRequest request, String id) {
        Experience experience = getEntityById(id);
        BeanUtils.copyProperties(request, experience);
        return convertToResponse(experienceRepository.save(experience));
    }

    @Override
    public void delete(String id) {
        experienceRepository.deleteById(id);
    }

    @Override
    public ExperienceResponse getById(String id) {
        return convertToResponse(getEntityById(id));
    }

    @Override
    public List<ExperienceResponse> getAllByProfileId(String profileId) {
        return experienceRepository.findByProfileId(profileId)
                .stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public Experience getEntityById(String id) {
        return experienceRepository.findById(id).orElseThrow(
            () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Experience not found")
        );
    }
}
