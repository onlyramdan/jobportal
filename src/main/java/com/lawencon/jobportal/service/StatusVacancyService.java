package com.lawencon.jobportal.service;


import com.lawencon.jobportal.model.request.MasterRequest;
import com.lawencon.jobportal.model.request.UpdateMasterRequest;
import com.lawencon.jobportal.model.response.MasterResponse;
import com.lawencon.jobportal.persistent.entity.StatusVacancy;

import java.util.List;

public interface StatusVacancyService {
    MasterResponse save(MasterRequest request);
    List<MasterResponse> findAll();
    MasterResponse findById(String id);
    StatusVacancy findEntityById(String id);
    StatusVacancy findByCode(String code);
    MasterResponse update(UpdateMasterRequest request);
    void delete(String id);
}
