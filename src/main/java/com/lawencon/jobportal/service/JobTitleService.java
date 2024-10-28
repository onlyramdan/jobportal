package com.lawencon.jobportal.service;


import com.lawencon.jobportal.model.request.MasterRequest;
import com.lawencon.jobportal.model.request.UpdateMasterRequest;
import com.lawencon.jobportal.model.response.MasterResponse;
import com.lawencon.jobportal.persistent.entity.JobTitle;

import java.util.List;

public interface JobTitleService {
    MasterResponse save(MasterRequest request);
    List<MasterResponse> findAll();
    MasterResponse findById(String id);
    JobTitle findEntityById(String  id);
    MasterResponse update(UpdateMasterRequest request);
    void  delete(String id);
}
