package com.lawencon.jobportal.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.lawencon.jobportal.helper.SpecificationHelper;
import com.lawencon.jobportal.model.request.MasterRequest;
import com.lawencon.jobportal.model.request.PagingRequest;
import com.lawencon.jobportal.model.request.UpdateMasterRequest;
import com.lawencon.jobportal.model.response.MasterResponse;
import com.lawencon.jobportal.persistent.entity.Location;
import com.lawencon.jobportal.persistent.repository.LocationRepository;
import com.lawencon.jobportal.service.LocationService;

import lombok.AllArgsConstructor;

import java.time.ZonedDateTime;
import java.time.ZoneOffset;

@Service
@AllArgsConstructor
public class LocationServiceImpl implements LocationService {
    private final  LocationRepository locationRepository;

    private MasterResponse converterResponse(Location  location) {
        MasterResponse masterResponse = new MasterResponse();
        BeanUtils.copyProperties(location, masterResponse);
        return masterResponse;
    }


    @Override
    public MasterResponse save(MasterRequest request) {
        Location location = new Location();
        isExistLocation(request.getCode());
        BeanUtils.copyProperties(request, location);
        location.setCreatedAt(ZonedDateTime.now(ZoneOffset.UTC));
        location.setUpdatedAt(ZonedDateTime.now(ZoneOffset.UTC));
        location.setCreatedBy("system");
        location.setUpdatedBy("system");
        location.setIsActive(true);
        return converterResponse(locationRepository.save(location));
    }

    @Override
    public List<MasterResponse> findAll() {
        return locationRepository.findAll().stream()
        .map(this::converterResponse)
        .collect(Collectors.toList());
    }

    @Override
    public MasterResponse findById(String id) {
        return converterResponse(findEntityById(id));
    }

    @Override
    public Location findEntityById(String id) {
        return locationRepository.findById(id).orElseThrow(
            () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Location Not Found")
        );
    }

    @Override
    public MasterResponse update(UpdateMasterRequest request) {
        Location location = findEntityById(request.getId());
        BeanUtils.copyProperties(request, location);
        return converterResponse(locationRepository.save(location));
    }


    @Override
    public void delete(String id) {
        locationRepository.deleteById(id);
    }


    @Override
    public Page<MasterResponse> findAll(PagingRequest pagingRequest, String inquiry) {
        PageRequest pageRequest  = PageRequest.of(pagingRequest.getPage(), pagingRequest.getPageSize(),
        SpecificationHelper.createSort(pagingRequest.getSortBy()));
        Specification<Location>  spec  = Specification.where(null);
        if (inquiry  != null) {
            spec = spec.and(SpecificationHelper.inquiryFilter(Arrays.asList("name","code"), inquiry));
        }

        Page<Location> locationResponse = locationRepository.findAll(spec,pageRequest);
        List<MasterResponse> responses = locationResponse.getContent().stream().map(location ->{
            MasterResponse response = new MasterResponse();
            BeanUtils.copyProperties(location, response);
            return response;
        }).toList();
        return new PageImpl<>(responses, pageRequest, locationResponse.getTotalElements());
    }

    private void isExistLocation(String code){
       if (locationRepository.existsByCode(code)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Location Code Already Exist");
       }
    }
}
