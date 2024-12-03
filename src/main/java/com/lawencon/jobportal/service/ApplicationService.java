package com.lawencon.jobportal.service;

import java.util.List;

import com.lawencon.jobportal.model.request.ApplicationRequest;
import com.lawencon.jobportal.model.request.UpadateStageRequest;
import com.lawencon.jobportal.model.response.ApplicationResponse;
import com.lawencon.jobportal.model.response.ApplicationResponseDetail;
import com.lawencon.jobportal.model.response.ApplicationTrxResponse;

public interface ApplicationService {
    void createApplication(ApplicationRequest request);

    ApplicationResponseDetail getApplicationById(String id);

    List<ApplicationResponse> getApllicationByCandidateId(String profileId);

    ApplicationTrxResponse updateAplicationStage(String aplicationTrxId, UpadateStageRequest request);
}
