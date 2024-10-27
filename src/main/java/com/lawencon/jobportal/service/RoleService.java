package com.lawencon.jobportal.service;

import com.lawencon.jobportal.model.request.MasterRequest;
import com.lawencon.jobportal.model.response.MasterResponse;
import com.lawencon.jobportal.persistent.entity.Role;

import java.util.List;

public interface RoleService {
    List<MasterResponse> findAll();
    MasterResponse findById(String id);
    MasterResponse findByCode(String code);
    MasterResponse save(MasterRequest request);
    MasterResponse update(Role role);
    void delete(String id);
    Role findEntityById(String id);
}
