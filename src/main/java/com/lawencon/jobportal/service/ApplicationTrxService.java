package com.lawencon.jobportal.service;

import com.lawencon.jobportal.model.request.ApplicationTrxRequest;
import com.lawencon.jobportal.model.response.ApplicationTrxResponse;

import java.util.List;

public interface ApplicationTrxService {
    ApplicationTrxResponse createApplicationTrx(ApplicationTrxRequest request);
    List<ApplicationTrxResponse> getAllTransactionsByApplicationId(String applicationId);
}
