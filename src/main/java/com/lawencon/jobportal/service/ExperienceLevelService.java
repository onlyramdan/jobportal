package com.lawencon.jobportal.service;


import com.lawencon.jobportal.model.request.MasterRequest;
import com.lawencon.jobportal.model.request.UpdateMasterRequest;
import com.lawencon.jobportal.model.response.MasterResponse;
import com.lawencon.jobportal.persistent.entity.ExperienceLevel;

import java.util.List;

public interface ExperienceLevelService {
    MasterResponse save(MasterRequest request);
    List<MasterResponse> findAll();
    MasterResponse findById(String id);
    ExperienceLevel findEntityById(String  id);
    MasterResponse update(UpdateMasterRequest request);
    void delete(String id);
    
}
