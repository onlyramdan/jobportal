package com.lawencon.jobportal.service;

import java.util.List;

import com.lawencon.jobportal.model.request.MasterRequest;
import com.lawencon.jobportal.model.request.UpdateMasterRequest;
import com.lawencon.jobportal.model.response.MasterResponse;
import com.lawencon.jobportal.persistent.entity.StatusAplication;

public interface StatusAplicationService {
    MasterResponse save(MasterRequest request);
    List<MasterResponse> findAll();
    MasterResponse findById(String id);
    StatusAplication findEntityById(String  id);
    MasterResponse update(UpdateMasterRequest request);
    void delete(String id);
}