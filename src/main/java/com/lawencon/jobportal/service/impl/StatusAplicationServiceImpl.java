package com.lawencon.jobportal.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.lawencon.jobportal.model.request.MasterRequest;
import com.lawencon.jobportal.model.request.UpdateMasterRequest;
import com.lawencon.jobportal.model.response.MasterResponse;
import com.lawencon.jobportal.persistent.entity.StatusAplication;
import com.lawencon.jobportal.persistent.repository.StatusAplicationRepository;
import com.lawencon.jobportal.service.StatusAplicationService;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;


import java.time.ZonedDateTime;
import java.time.ZoneOffset;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class StatusAplicationServiceImpl implements StatusAplicationService {

    private final StatusAplicationRepository  statusAplicationRepository;

    private MasterResponse converterResponse(StatusAplication  statusAplication) {
        MasterResponse masterResponse = new MasterResponse();
        BeanUtils.copyProperties(statusAplication, masterResponse);
        return masterResponse;
    }

    @Override
    public void delete(String id) {
        statusAplicationRepository.deleteById(id);
    }

    @Override
    public List<MasterResponse> findAll() {
        return statusAplicationRepository.findAll().stream().map(status->
        converterResponse(status)).collect(Collectors.toList());
    }

    @Override
    public MasterResponse findById(String id) {
        return converterResponse(findEntityById(id));

    }

    @Override
    public StatusAplication findEntityById(String id) {
        return statusAplicationRepository.findById(id).orElseThrow(
            () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "StatusAplication Not Found"));
    }

    @Override
    public MasterResponse save(MasterRequest request) {
        StatusAplication statusAplication = new StatusAplication();
        BeanUtils.copyProperties(request, statusAplication);
        statusAplication.setCreatedAt(ZonedDateTime.now(ZoneOffset.UTC));
        statusAplication.setUpdatedAt(ZonedDateTime.now(ZoneOffset.UTC));
        statusAplication.setCreatedBy("system");
        statusAplication.setUpdatedBy("system");
        statusAplication.setIsActive(true);
        return converterResponse(statusAplicationRepository.save(statusAplication));
    }

    @Override
    public MasterResponse update(UpdateMasterRequest request) {
        StatusAplication statusAplication = findEntityById(request.getId());
        BeanUtils.copyProperties(request, statusAplication);
        return converterResponse(statusAplicationRepository.save(statusAplication)); 
    }
}
