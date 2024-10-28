package com.lawencon.jobportal.service;

import com.lawencon.jobportal.model.request.CreateVacancyTrxRequest;
import com.lawencon.jobportal.persistent.entity.StatusVacancyTrx;

import java.util.List;

public interface StatusVacancyTrxService {
    List<StatusVacancyTrx> findAllByVacancyId(String id);
    StatusVacancyTrx findByVacancyId(String id);
    StatusVacancyTrx save(CreateVacancyTrxRequest request);
    StatusVacancyTrx update(String id, CreateVacancyTrxRequest request);
    void delete(String id);
}
