package com.lawencon.jobportal.service;

import com.lawencon.jobportal.model.request.UpadateStageRequest;
import com.lawencon.jobportal.model.response.ApplicationTrxResponse;
import com.lawencon.jobportal.persistent.entity.ApplicationTrx;

import java.util.List;

public interface ApplicationTrxService {
    ApplicationTrxResponse createApplicationTrx(ApplicationTrx request);

    List<ApplicationTrxResponse> getAllTransactionsByApplicationId(String applicationId);

    ApplicationTrxResponse updateAplicationTrx(String id, UpadateStageRequest request);

    ApplicationTrx findEntityById(String id);

}
