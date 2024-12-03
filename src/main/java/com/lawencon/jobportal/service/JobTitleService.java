package com.lawencon.jobportal.service;


import java.util.List;

import org.springframework.data.domain.Page;

import com.lawencon.jobportal.model.request.PagingRequest;
import com.lawencon.jobportal.model.request.UpdateMasterRequest;
import com.lawencon.jobportal.model.response.MasterResponse;
import com.lawencon.jobportal.persistent.entity.JobTitle;

public interface JobTitleService {
    MasterResponse save(String request);
    List<MasterResponse> findAll();
    MasterResponse findById(String id);
    JobTitle findEntityById(String  id);
    MasterResponse update(UpdateMasterRequest request);
    void delete(String id);
    Page<MasterResponse> findAll(PagingRequest pagingRequest, String inquiry);
    Long countAll();
}
