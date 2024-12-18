package com.lawencon.jobportal.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.lawencon.jobportal.model.request.CreateProfileRequest;
import com.lawencon.jobportal.model.response.ProfileResponse;
import com.lawencon.jobportal.persistent.entity.Profile;
import com.lawencon.jobportal.persistent.repository.ProfileRepository;
import com.lawencon.jobportal.service.ProfileService;

import io.micrometer.common.util.StringUtils;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;


import java.time.ZonedDateTime;
import java.util.Optional;
import java.time.ZoneOffset;


@Service
@AllArgsConstructor
@Transactional
public class ProfileServiceImpl implements  ProfileService {
    private final ProfileRepository  profileRepository;

    private ProfileResponse converterResponse(Profile profile){
        ProfileResponse response = new ProfileResponse();
        BeanUtils.copyProperties(profile, response);
        response.setUserId(profile.getUser().getId());
        return response;
    }

    @Override
    public void delete(String id) {
        profileRepository.deleteById(id);
    }

    @Override
    public ProfileResponse findById(String id) {
        return converterResponse(findEntityById(id));
    }

    @Override
    public ProfileResponse findByUserId(String id) {
        Profile  profile = profileRepository.findByUserId(id).orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Profile not found")
        );
        return converterResponse(profile);
    }

    @Override
    public Profile findEntityById(String id) {
        return profileRepository.findById(id).orElseThrow(
            () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Profile Not Found")
        );
    }

    @Override
    public ProfileResponse save(CreateProfileRequest request) {
        Profile profile = new Profile();
        profile.setUser(request.getUser());
        profile.setCreatedAt(ZonedDateTime.now(ZoneOffset.UTC));
        profile.setUpdatedAt(ZonedDateTime.now(ZoneOffset.UTC));
        profile.setCreatedBy("system");
        profile.setUpdatedBy("system");
        profile.setIsActive(true);
        return converterResponse(profileRepository.save(profile));
    }

    @Override
    public ProfileResponse update(CreateProfileRequest request, String id) {
        Profile profile = findEntityById(id);
        BeanUtils.copyProperties(request, profile);
        return  converterResponse(profileRepository.save(profile));
    }

    @Override
    public Boolean checkProfileExist(String id) {
        Optional<Profile> profile = profileRepository.findById(id);
        if(!profile.isPresent()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Profile Not Found");
        }
        Profile existingProfile = profile.get();
        boolean hasFullName = !StringUtils.isBlank(existingProfile.getFullName());
        boolean hasPhone = !StringUtils.isBlank(existingProfile.getPhoneNumber());
        return hasFullName && hasPhone;
    }

    @Override
    public Optional<Profile> existUserId(String id) {
        return profileRepository.findByUserId(id);
    }
    
    
}
