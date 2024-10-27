package com.lawencon.jobportal.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.lawencon.jobportal.model.request.MasterRequest;
import com.lawencon.jobportal.model.response.MasterResponse;
import com.lawencon.jobportal.persistent.entity.Role;
import com.lawencon.jobportal.persistent.repository.RoleRepository;
import com.lawencon.jobportal.service.RoleService;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

import java.time.ZonedDateTime;
import java.time.ZoneOffset;

@Service
@Transactional
@AllArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    private MasterResponse converterResponse(Role  role) {
        MasterResponse masterResponse = new MasterResponse();
        BeanUtils.copyProperties(role, masterResponse);
        return masterResponse;
    }


    @Override
    public List<MasterResponse> findAll() {
        return roleRepository.findAll().stream()
            .map(this::converterResponse)
            .collect(Collectors.toList());
        }

    @Override
    public MasterResponse findById(String id) {
        return converterResponse(findEntityById(id));
    }

    @Override
    public MasterResponse save(MasterRequest request) {
        Role role =  new Role();
        BeanUtils.copyProperties(request, role);
        role.setCreatedAt(ZonedDateTime.now(ZoneOffset.UTC));
        role.setUpdatedAt(ZonedDateTime.now(ZoneOffset.UTC));
        role.setCreatedBy("system");
        role.setUpdatedBy("system");
        role.setIsActive(true);
        return converterResponse(roleRepository.save(role));
    }

    @Override
    public MasterResponse update(Role role) {
        Role roleUpdate = findEntityById(role.getId());
        BeanUtils.copyProperties(role, roleUpdate);
        return converterResponse(roleRepository.save(roleUpdate));
    }

    @Override
    public void delete(String id) {
        roleRepository.deleteById(id);
    }

    @Override
    public MasterResponse findByCode(String code) {
        return  roleRepository.findByCode(code).map(this::converterResponse).orElseThrow(
            () -> new  ResponseStatusException(HttpStatus.BAD_REQUEST, "Role Not Found")
        );

    }


    @Override
    public Role findEntityById(String id) {
        return roleRepository.findById(id).orElseThrow(
            () ->  new  ResponseStatusException(HttpStatus.BAD_REQUEST, "Role Not Found")
        );
    }    
}
