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
import com.lawencon.jobportal.persistent.entity.EmployeeType;
import com.lawencon.jobportal.persistent.repository.EmployeeTypeRepository;
import com.lawencon.jobportal.service.EmployeeTypeService;

import lombok.AllArgsConstructor;

import java.time.ZonedDateTime;
import java.time.ZoneOffset;

@Service
@AllArgsConstructor
public class EmployeeTypeServiceImpl implements  EmployeeTypeService {
    private final EmployeeTypeRepository  employeeTypeRepository;

    private MasterResponse converterResponse(EmployeeType  employeeType) {
        MasterResponse masterResponse = new MasterResponse();
        BeanUtils.copyProperties(employeeType, masterResponse);
        return masterResponse;
    }

    @Override
    public void delete(String id) {
        employeeTypeRepository.deleteById(id);
    }

    @Override
    public List<MasterResponse> findAll() {
        return employeeTypeRepository.findAll().stream().map(type->
        converterResponse(type)).collect(Collectors.toList());

    }

    @Override
    public MasterResponse findById(String id) {
        return converterResponse(findEntityById(id));

    }

    @Override
    public EmployeeType findEntityById(String id) {
        return employeeTypeRepository.findById(id).orElseThrow(
            () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Employee Type Not Found"));
    }

    @Override
    public MasterResponse save(MasterRequest request) {
        EmployeeType employeeType = new EmployeeType();
        BeanUtils.copyProperties(request, employeeType);
        employeeType.setCreatedAt(ZonedDateTime.now(ZoneOffset.UTC));
        employeeType.setUpdatedAt(ZonedDateTime.now(ZoneOffset.UTC));
        employeeType.setCreatedBy("system");
        employeeType.setUpdatedBy("system");
        employeeType.setIsActive(true);
        return converterResponse(employeeTypeRepository.save(employeeType));
    }

    @Override
    public MasterResponse update(UpdateMasterRequest request) {
        EmployeeType employeeType = findEntityById(request.getId());
        BeanUtils.copyProperties(request, employeeType);
        return converterResponse(employeeTypeRepository.save(employeeType)); 
    }
        
}
